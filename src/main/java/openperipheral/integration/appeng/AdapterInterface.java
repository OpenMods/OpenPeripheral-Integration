package openperipheral.integration.appeng;

import openmods.inventory.legacy.ItemDistribution;
import openmods.reflection.MethodAccess;
import openmods.utils.InventoryUtils;
import openperipheral.api.*;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;

import dan200.computercraft.api.peripheral.IComputerAccess;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;

public class AdapterInterface extends AbstractGridAdapter implements IPeripheralAdapter {
	private final MethodAccess.Function0<EnumSet> GET_TARGETS;

	public AdapterInterface() {
		super("appeng.tile.misc.TileInterface");

		GET_TARGETS = MethodAccess.create(EnumSet.class, CLASS, "getTargets");
	}

	@Override
	public String getSourceId() {
		return "me_interface";
	}

	// Method using reflection to determine the side the ME Interface is
	// currently
	// pointing to. This is a helper method used to check whether the ME
	// Interface
	// is really pointing at an IInventory later on.
	private ForgeDirection getPointedAt(Object tileEntityInterface) {
		EnumSet<ForgeDirection> targets = GET_TARGETS.call(tileEntityInterface);
		if (targets.size() != 1) { return null; }

		return targets.iterator().next();
	}

	// Another helper method. Returns the IInventory the ME Interface is
	// pointing
	// at. Or null.
	private IInventory getNeighborInventory(Object tileEntityInterface) {
		ForgeDirection dir = getPointedAt(tileEntityInterface);
		if (dir == null) { return null; }

		TileEntity tileEntity = (TileEntity)tileEntityInterface;

		return InventoryUtils.getInventory(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, dir);
	}

	@LuaCallable(description = "Returns true when the interface is pointing at a valid inventory.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean hasValidTarget(Object tileEntityInterface) {
		if (getNeighborInventory(tileEntityInterface) != null) { return true; }

		return false;
	}

	@LuaCallable(description = "Retrieves details about the specified item from the ME Network.", returnTypes = LuaReturnType.TABLE)
	public ItemStack getItemDetail(Object tileEntityInterface,
			@Arg(name = "item", description = "Details of the item you are looking for: { id, [ dmg, [nbt_id]] }", type = LuaArgType.TABLE) SearchNeedle needle) {
		// Search for the item in the storage grid and return it if found.
		// Nothing fancy about this.
		for (IAEItemStack stack : getStorageGrid(tileEntityInterface).getItemInventory().getStorageList()) {
			if (needle.compareToAEStack(stack)) {
				// We're wringing this through our nbt-hash provider so the
				// nbt-id
				// will get included in the result.
				return NBTHashMetaProvider.convertStacks(stack);
			}
		}

		return null;
	}

	@LuaCallable(description = "Requests the specified item to get crafted.")
	public void requestCrafting(Object tileEntityInterface,
			@Env("computer") IComputerAccess computer,
			@Arg(name = "item", description = "Details of the item you want to craft: { id, [ dmg, [nbt_id]] }", type = LuaArgType.TABLE) SearchNeedle needle,
			@Optionals @Arg(name = "qty", description = "The quantity of items you want to craft") Integer quantity,
			@Arg(name = "cpu", description = "The name of the CPU you want to use") String wantedCpuName) {
		// Get access to the crafting grid of the ME network
		ICraftingGrid craftingGrid = getCraftingGrid(tileEntityInterface);

		// If the lua code does not specify a quantity to request, we assume he
		// meant 1
		if (quantity == null) {
			quantity = 1;
		}

		// By default and when passing null to submitJob the first free CPU will
		// be used.
		// We want to allow the lua program to choose which CPU should be used
		// so we need
		// to find the correct ICraftingCPU to the cpuName we got passed from
		// lua code.
		// If no CPU with that name exists, we will use the first free one as
		// well.
		// If several CPUs with the same name exist, we will use the first one
		// of those.
		ICraftingCPU wantedCpu = null;
		if (wantedCpuName != null) {
			for (ICraftingCPU cpu : craftingGrid.getCpus()) {
				if (cpu.getName().equals(wantedCpuName)) {
					// Found it!
					wantedCpu = cpu;
					break;
				}
			}
		}

		// First make sure the item exists in the network and is craftable
		// Get the storage data from the grid
		IStorageGrid storageGrid = getStorageGrid(tileEntityInterface);
		IMEMonitor<IAEItemStack> monitor = storageGrid.getItemInventory();

		// Iterate over it's contents to get the stack we're looking for
		for (IAEItemStack stack : monitor.getStorageList()) {
			if (stack.isCraftable() && needle.compareToAEStack(stack)) {
				// Found it! As we don't want to manipulate the original stack
				// we're grabbing ourselves
				// a copy and set its quantity to the one specified by the lua
				// code. We're doing this
				// because we're using this stack to actually request the item
				// from the network.
				IAEItemStack copy = stack.copy();
				copy.setStackSize(quantity);

				// Create a new CraftingCallback. This callback is called when
				// the network finished
				// calculating the required items. It can do two things for us:
				// a) It sends an event when items are missing to complete the
				// request
				// b) Otherwise it starts the crafting job, which itself is a
				// CraftingRequester
				// sending more events to the computer.
				CraftingCallback craftingCallback = new CraftingCallback(computer, craftingGrid, monitor, (IActionHost)tileEntityInterface, wantedCpu, copy);

				// We will need access to the worldObj of the ME Interface ->
				// cast to TileEntity
				TileEntity tileEntity = (TileEntity)tileEntityInterface;

				// Tell the craftingGrid to begin calculating and to report
				// everything to the CraftingCallback
				craftingGrid.beginCraftingJob(tileEntity.getWorldObj(), getGridNode(tileEntityInterface).getGrid(), new MachineSource((IActionHost)tileEntityInterface), copy, craftingCallback);
				break;
			}
		}
	}

	@LuaCallable(description = "Exports the specified item into the target inventory.", returnTypes = LuaReturnType.TABLE)
	public ItemStack exportItem(Object tileEntityInterface,
			@Arg(name = "item", description = "Details of the item you want to export", type = LuaArgType.TABLE) SearchNeedle needle,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to export") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the other inventory that you want to export into") Integer intoSlot) {

		// We can only export an item from the network if the interface is
		// explicitly pointing at
		// an inventory. Abort and return nil if that's not the case.
		if (!hasValidTarget(tileEntityInterface)) { return null; }

		// If no specific target slot has been specified use the best one we can
		// find.
		if (intoSlot == null) {
			intoSlot = -1;
		}

		// Get access to the network and its item storage
		IStorageGrid storageGrid = getStorageGrid(tileEntityInterface);
		IMEMonitor<IAEItemStack> monitor = storageGrid.getItemInventory();

		// Search for the item by using the id, dmg and a hash over the nbt
		// data; Take a look at
		// SearchNeedle for more details.
		for (IAEItemStack stack : monitor.getStorageList()) {
			if (needle.compareToAEStack(stack)) {
				// Found it!
				// Make sure we are only requesting MaxStackSize items and more
				// than 0
				IAEItemStack copy = stack.copy();
				if (maxAmount == null || maxAmount < 1 || maxAmount > copy.getItemStack().getMaxStackSize()) {
					copy.setStackSize(copy.getItemStack().getMaxStackSize());
				} else {
					copy.setStackSize(maxAmount);
				}

				// Actually export the items from the ME system.
				MachineSource machineSource = new MachineSource((IActionHost)tileEntityInterface);
				IAEItemStack out = monitor.extractItems(copy, Actionable.MODULATE, machineSource);
				ItemStack exportedStack = out.getItemStack().copy();

				// Put the item in the neighbor inventory
				ItemDistribution.insertItemIntoInventory(getNeighborInventory(tileEntityInterface), exportedStack, getPointedAt(tileEntityInterface).getOpposite(), intoSlot);

				// If we've moved some items, but others are still remaining
				// insert them back into the ME system.
				if (exportedStack.stackSize > 0) {
					monitor.injectItems(AEApi.instance().storage().createItemStack(exportedStack), Actionable.MODULATE, machineSource);
					copy.setStackSize(copy.getStackSize() - exportedStack.stackSize);
				}

				// Return what we've actually extracted
				return copy.getItemStack();
			}
		}

		return null;
	}
}
