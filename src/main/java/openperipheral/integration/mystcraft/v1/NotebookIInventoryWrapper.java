package openperipheral.integration.mystcraft.v1;

import com.google.common.base.Preconditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.MethodAccess.Function2;
import openmods.reflection.MethodAccess.Function3;
import openmods.reflection.ReflectionHelper;

public class NotebookIInventoryWrapper implements IInventory {

	private final Class<?> INVENTORY_CLASS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.inventory.InventoryNotebook");

	private final Function1<Boolean, ItemStack> IS_ITEM_VALID = MethodAccess.create(boolean.class, INVENTORY_CLASS, ItemStack.class, "isItemValid");
	private final Function1<String, ItemStack> GET_NAME = MethodAccess.create(String.class, INVENTORY_CLASS, ItemStack.class, "getName");
	private final Function1<Integer, ItemStack> GET_ITEM_COUNT = MethodAccess.create(Integer.class, INVENTORY_CLASS, ItemStack.class, "getItemCount");
	private final Function1<Integer, ItemStack> GET_LARGEST_SLOT_ID = MethodAccess.create(int.class, INVENTORY_CLASS, ItemStack.class, "getLargestSlotId");
	private final Function2<ItemStack, ItemStack, Integer> GET_ITEM = MethodAccess.create(ItemStack.class, INVENTORY_CLASS, ItemStack.class, int.class, "getItem");
	private final Function3<Void, ItemStack, Integer, ItemStack> SET_ITEM = MethodAccess.create(void.class, INVENTORY_CLASS, ItemStack.class, int.class, ItemStack.class, "setItem");

	private ItemStack notebook;

	public NotebookIInventoryWrapper(ItemStack notebook) {
		Preconditions.checkNotNull(notebook, "No notebook");
		this.notebook = notebook;
	}

	@Override
	public int getSizeInventory() {
		int slotId = GET_LARGEST_SLOT_ID.call(null, notebook);
		return slotId + 2;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return GET_ITEM.call(null, notebook, i);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack == null) return null;
		if (stack.stackSize < amount) amount = stack.stackSize;

		ItemStack returning = stack.copy();
		returning.stackSize = amount;

		stack.stackSize -= amount;
		if (stack.stackSize <= 0) setInventorySlotContents(slot, stack);

		return returning;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack pageStack) {
		SET_ITEM.call(null, notebook, slot, pageStack);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String getInventoryName() {
		return GET_NAME.call(null, notebook);
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return Integer.MAX_VALUE; // I don't believe there is any limit to the
									// storage of a notebook
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return IS_ITEM_VALID.call(null, itemstack);
	}

	public Integer getItemCount() {
		return GET_ITEM_COUNT.call(null, notebook);
	}
}
