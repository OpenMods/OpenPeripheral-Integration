package openperipheral.integration.appeng;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import openmods.inventory.legacy.ItemDistribution;
import openmods.reflection.ReflectionHelper;
import openmods.utils.InventoryUtils;
import openperipheral.api.*;
import openperipheral.integration.vanilla.ItemFingerprint;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;

import com.google.common.base.Preconditions;

public class AdapterInterface extends AdapterGridBase {
	private final Class<?> CLASS = ReflectionHelper.getClass("appeng.tile.misc.TileInterface");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "me_interface";
	}

	@LuaCallable(description = "Requests the specified item to get crafted.")
	public void requestCrafting(IActionHost host,
			@Env(Constants.ARG_ACCESS) IArchitectureAccess access,
			@Env(Constants.ARG_CONVERTER) ITypeConvertersRegistry converter,
			@Arg(name = "item", description = "Details of the item you want to craft. Can be found with .getStackFingerprint on inventory and .getAvailableItems on AE network", type = LuaArgType.TABLE) ItemFingerprint needle,
			@Optionals @Arg(name = "qty", description = "The quantity of items you want to craft") Long quantity,
			@Arg(name = "cpu", description = "The name of the CPU you want to use") String wantedCpuName) {
		ICraftingGrid craftingGrid = getCraftingGrid(host);
		if (quantity == null) quantity = 1L;

		ICraftingCPU wantedCpu = findCpu(craftingGrid, wantedCpuName);

		IStorageGrid storageGrid = getStorageGrid(host);
		IMEMonitor<IAEItemStack> monitor = storageGrid.getItemInventory();

		IAEItemStack stack = findCraftableStack(storageGrid.getItemInventory().getStorageList(), needle);
		if (stack == null) {
			throw new IllegalArgumentException(String.format("Can't find craftable item with fingerprint %s", needle));
		}

		final IAEItemStack toCraft = stack.copy();
		toCraft.setStackSize(quantity);

		// Create a new CraftingCallback. This callback is called when
		// the network finished calculating the required items. It can do two things for us:
		// a) It sends an event when items are missing to complete the request
		// b) Otherwise it starts the crafting job, which itself is a CraftingRequester OSsending more events to the computer.
		final CraftingCallback craftingCallback = new CraftingCallback(access, converter, craftingGrid, monitor, host, wantedCpu, toCraft);

		// We will need access to the worldObj of the ME Interface -> cast to TileEntity
		final TileEntity tileEntity = (TileEntity)host;

		// Tell the craftingGrid to begin calculating and to report everything to the CraftingCallback
		craftingGrid.beginCraftingJob(tileEntity.getWorldObj(), getGrid(host), new MachineSource(host), toCraft, craftingCallback);

	}

	@LuaCallable(description = "Returns true when the interface can extract to side.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canExtract(Object tileEntityInterface, @Arg(name = "direction", description = "Location of target inventory") ForgeDirection direction) {
		return getNeighborInventory(tileEntityInterface, direction) != null;
	}

	@LuaCallable(description = "Exports the specified item into the target inventory.", returnTypes = LuaReturnType.TABLE)
	public IAEItemStack exportItem(Object tileEntityInterface,
			@Arg(name = "item", description = "Details of the item you want to export", type = LuaArgType.TABLE) ItemFingerprint needle,
			@Arg(name = "direction", description = "Location of target inventory") ForgeDirection direction,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to export") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the other inventory that you want to export into") Integer intoSlot) {

		final IActionHost host = (IActionHost)tileEntityInterface;
		final IInventory neighbor = getNeighborInventory(tileEntityInterface, direction);
		Preconditions.checkArgument(neighbor != null, "No neighbour attached");

		if (intoSlot == null) intoSlot = -1;

		IStorageGrid storageGrid = getStorageGrid(host);
		IMEMonitor<IAEItemStack> monitor = storageGrid.getItemInventory();

		IAEItemStack stack = findStack(monitor.getStorageList(), needle);
		if (stack == null) {
			throw new IllegalArgumentException(String.format("Can't find item with fingerprint %s", needle));
		}

		IAEItemStack toExtract = stack.copy();
		if (maxAmount == null || maxAmount < 1 || maxAmount > toExtract.getItemStack().getMaxStackSize()) {
			toExtract.setStackSize(toExtract.getItemStack().getMaxStackSize());
		} else {
			toExtract.setStackSize(maxAmount);
		}

		// Actually export the items from the ME system.
		MachineSource machineSource = new MachineSource(host);
		IAEItemStack extracted = monitor.extractItems(toExtract, Actionable.MODULATE, machineSource);
		if (extracted == null) return null;

		ItemStack toInsert = extracted.getItemStack().copy();

		// Put the item in the neighbor inventory
		ItemDistribution.insertItemIntoInventory(neighbor, toInsert, direction.getOpposite(), intoSlot);

		// If we've moved some items, but others are still remaining.
		// Insert them back into the ME system.
		if (toInsert.stackSize > 0) {
			final IAEItemStack toReturn = AEApi.instance().storage().createItemStack(toInsert);
			monitor.injectItems(toReturn, Actionable.MODULATE, machineSource);
			extracted.decStackSize(toInsert.stackSize);
		}

		// Return what we've actually extracted
		return extracted;
	}

	private static ICraftingCPU findCpu(ICraftingGrid craftingGrid, String cpuName) {
		if (cpuName != null) {
			for (ICraftingCPU cpu : craftingGrid.getCpus())
				if (cpu.getName().equals(cpuName)) return cpu;
		}

		return null;
	}

	private static IInventory getNeighborInventory(Object te, ForgeDirection dir) {
		TileEntity tileEntity = (TileEntity)te;
		return InventoryUtils.getInventory(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, dir);
	}
}
