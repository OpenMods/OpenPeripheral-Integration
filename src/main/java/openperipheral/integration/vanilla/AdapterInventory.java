package openperipheral.integration.vanilla;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.inventory.legacy.ItemDistribution;
import openmods.utils.InventoryUtils;
import openperipheral.api.*;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Asynchronous
public class AdapterInventory implements IPeripheralAdapter {

	private static final int ANY_SLOT = -1;

	@Override
	public Class<?> getTargetClass() {
		return IInventory.class;
	}

	@Override
	public String getSourceId() {
		return "inventory";
	}

	@LuaCallable(returnTypes = LuaReturnType.STRING, description = "Get the name of this inventory")
	public String getInventoryName(IInventory target) {
		IInventory inventory = InventoryUtils.getInventory(target);
		return inventory != null? inventory.getInventoryName() : null;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Get the size of this inventory")
	public int getInventorySize(IInventory target) {
		IInventory inventory = InventoryUtils.getInventory(target);
		return inventory != null? inventory.getSizeInventory() : 0;
	}

	@LuaCallable(description = "Condense and tidy the stacks in an inventory")
	public void condenseItems(IInventory target) {
		IInventory inventory = InventoryUtils.getInventory(target);
		List<ItemStack> stacks = Lists.newArrayList();
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack sta = inventory.getStackInSlot(i);
			if (sta != null) stacks.add(sta.copy());
			inventory.setInventorySlotContents(i, null);
		}

		for (ItemStack stack : stacks)
			ItemDistribution.insertItemIntoInventory(inventory, stack, ForgeDirection.UNKNOWN, ANY_SLOT);

		target.markDirty();
	}

	@LuaCallable(description = "Swap two slots in the inventory")
	public void swapStacks(IInventory target,
			@Arg(name = "from", description = "The first slot") int fromSlot,
			@Arg(name = "to", description = "The other slot") int intoSlot,
			@Optionals @Arg(name = "fromDirection") ForgeDirection fromDirection,
			@Arg(name = "fromDirection") ForgeDirection toDirection) {
		IInventory inventory = InventoryUtils.getInventory(target);
		Preconditions.checkNotNull(inventory, "Invalid target!");
		if (inventory instanceof ISidedInventory) {
			InventoryUtils.swapStacks((ISidedInventory)inventory,
					fromSlot - 1, Objects.firstNonNull(fromDirection, ForgeDirection.UNKNOWN),
					intoSlot - 1, Objects.firstNonNull(toDirection, ForgeDirection.UNKNOWN));
		} else InventoryUtils.swapStacks(inventory, fromSlot - 1, intoSlot - 1);

		inventory.markDirty();
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Get details of an item in a particular slot")
	public ItemStack getStackInSlot(IInventory target,
			@Arg(name = "slotNumber", description = "The slot number, from 1 to the max amount of slots") int slot)
	{
		IInventory invent = InventoryUtils.getInventory(target);
		slot -= 1;
		Preconditions.checkElementIndex(slot, invent.getSizeInventory(), "slot id");
		return invent.getStackInSlot(slot);
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Get a table with all the items of the chest")
	public ItemStack[] getAllStacks(IInventory target) {
		IInventory inventory = InventoryUtils.getInventory(target);
		ItemStack[] allStacks = new ItemStack[inventory.getSizeInventory()];
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			allStacks[i] = inventory.getStackInSlot(i);
		}
		return allStacks;
	}

	@LuaCallable(description = "Destroy a stack")
	public void destroyStack(IInventory target,
			@Arg(name = "slotNumber", description = "The slot number, from 1 to the max amount of slots") int slot)
	{
		IInventory inventory = InventoryUtils.getInventory(target);
		slot -= 1;
		Preconditions.checkElementIndex(slot, inventory.getSizeInventory(), "slot id");
		inventory.setInventorySlotContents(slot, null);
		inventory.markDirty();
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Get full stack information from stub one {id=..., [qty=...], [dmg=...]}")
	public ItemStack expandStack(@Arg(name = "stack", type = LuaArgType.TABLE) ItemStack itemStack) {
		return itemStack;
	}
}
