package openperipheral.integration.appeng;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.IGridHost;
import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;
import com.google.common.base.Preconditions;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.inventory.legacy.ItemDistribution;
import openmods.reflection.ReflectionHelper;
import openmods.utils.InventoryUtils;
import openperipheral.api.Constants;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.Env;
import openperipheral.api.adapter.method.Optionals;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.api.architecture.IArchitectureAccess;
import openperipheral.api.converter.IConverter;
import openperipheral.api.helpers.Index;
import openperipheral.integration.vanilla.ItemFingerprint;

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

	@ScriptCallable(description = "Requests the specified item to get crafted.")
	public void requestCrafting(IActionHost host,
			@Env(Constants.ARG_ACCESS) IArchitectureAccess access,
			@Env(Constants.ARG_CONVERTER) IConverter converter,
			@Arg(name = "fingerprint", description = "Details of the item you want to craft. Can be found with .getStackInSlot on inventory and .getAvailableItems on AE network") ItemFingerprint needle,
			@Optionals @Arg(name = "qty", description = "The quantity of items you want to craft") Long quantity,
			@Arg(name = "cpu", description = "The name of the CPU you want to use") String wantedCpuName) {
		ICraftingGrid craftingGrid = getCraftingGrid(host);
		if (quantity == null) quantity = 1L;

		ICraftingCPU wantedCpu = findCpu(craftingGrid, wantedCpuName);

		IStorageGrid storageGrid = getStorageGrid(host);
		IMEMonitor<IAEItemStack> monitor = storageGrid.getItemInventory();

		IAEItemStack stack = findCraftableStack(storageGrid.getItemInventory().getStorageList(), needle);
		Preconditions.checkArgument(stack != null, "Can't find craftable item fingerprint %s", needle);

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

	@ScriptCallable(description = "Returns true when the interface can export to side.", returnTypes = ReturnType.BOOLEAN)
	public boolean canExport(IGridHost tileEntityInterface, @Arg(name = "direction", description = "Location of target inventory") ForgeDirection direction) {
		return getNeighborInventory(tileEntityInterface, direction) != null;
	}

	@ScriptCallable(description = "Exports the specified item into the target inventory.", returnTypes = ReturnType.TABLE)
	public IAEItemStack exportItem(Object tileEntityInterface,
			@Arg(name = "fingerprint", description = "Details of the item you want to export (can be result of .getStackInSlot() or .getAvailableItems())") ItemFingerprint needle,
			@Arg(name = "direction", description = "Location of target inventory") ForgeDirection direction,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to export") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the other inventory that you want to export into") Index intoSlot) {

		final IActionHost host = (IActionHost)tileEntityInterface;
		final IInventory neighbor = getNeighborInventory(tileEntityInterface, direction);
		Preconditions.checkArgument(neighbor != null, "No neighbour attached");

		if (intoSlot == null) intoSlot = Index.fromJava(-1, 0);

		IStorageGrid storageGrid = getStorageGrid(host);
		IMEMonitor<IAEItemStack> monitor = storageGrid.getItemInventory();

		IAEItemStack stack = findStack(monitor.getStorageList(), needle);
		Preconditions.checkArgument(stack != null, "Can't find item fingerprint %s", needle);

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
		if (ItemDistribution.insertItemIntoInventory(neighbor, toInsert, direction.getOpposite(), intoSlot.value)) {
			neighbor.markDirty();
		}

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
