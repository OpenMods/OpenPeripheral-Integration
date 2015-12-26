package openperipheral.integration.vanilla;

import java.util.List;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.inventory.legacy.ItemDistribution;
import openmods.utils.InventoryUtils;
import openperipheral.api.Constants;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;
import openperipheral.api.architecture.IArchitecture;
import openperipheral.api.helpers.Index;
import openperipheral.integration.OpcAccess;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Get the name of this inventory")
	public String getInventoryName(IInventory target) {
		IInventory inventory = InventoryUtils.getInventory(target);
		return inventory != null? inventory.getInventoryName() : null;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the size of this inventory")
	public int getInventorySize(IInventory target) {
		IInventory inventory = InventoryUtils.getInventory(target);
		return inventory != null? inventory.getSizeInventory() : 0;
	}

	@Asynchronous(false)
	@ScriptCallable(description = "Condense and tidy the stacks in an inventory")
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

	@Asynchronous(false)
	@ScriptCallable(description = "Swap two slots in the inventory")
	public void swapStacks(IInventory target,
			@Arg(name = "from", description = "The first slot") Index fromSlot,
			@Arg(name = "to", description = "The other slot") Index intoSlot,
			@Optionals @Arg(name = "fromDirection") ForgeDirection fromDirection,
			@Arg(name = "fromDirection") ForgeDirection toDirection) {
		IInventory inventory = InventoryUtils.getInventory(target);
		Preconditions.checkNotNull(inventory, "Invalid target!");
		final int size = inventory.getSizeInventory();
		fromSlot.checkElementIndex("first slot id", size);
		intoSlot.checkElementIndex("second slot id", size);
		if (inventory instanceof ISidedInventory) {
			InventoryUtils.swapStacks((ISidedInventory)inventory,
					fromSlot.value, Objects.firstNonNull(fromDirection, ForgeDirection.UNKNOWN),
					intoSlot.value, Objects.firstNonNull(toDirection, ForgeDirection.UNKNOWN));
		} else InventoryUtils.swapStacks(inventory, fromSlot.value, intoSlot.value);

		inventory.markDirty();
	}

	@ScriptCallable(returnTypes = ReturnType.OBJECT, description = "Get details of an item in a particular slot")
	public Object getStackInSlot(IInventory target,
			@Arg(name = "slotNumber", description = "Slot number") Index slot,
			@Optionals @Arg(name = "proxy", description = "If true, method will return proxy instead of computing whole table") Boolean proxy)
	{
		IInventory inventory = InventoryUtils.getInventory(target);
		slot.checkElementIndex("slot id", inventory.getSizeInventory());
		ItemStack stack = inventory.getStackInSlot(slot.value);
		return proxy == Boolean.TRUE? OpcAccess.itemStackMetaBuilder.createProxy(stack) : stack;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get a table with all the items of the chest")
	public Map<Index, Object> getAllStacks(IInventory target,
			@Env(Constants.ARG_ARCHITECTURE) IArchitecture access,
			@Optionals @Arg(name = "proxy", description = "If false, method will compute whole table, instead of returning proxy") Boolean proxy) {
		final IInventory inventory = InventoryUtils.getInventory(target);
		Map<Index, Object> result = Maps.newHashMap();

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) result.put(access.createIndex(i), (proxy != Boolean.FALSE)? OpcAccess.itemStackMetaBuilder.createProxy(stack) : stack);
		}
		return result;
	}

	@Asynchronous(false)
	@ScriptCallable(description = "Destroy a stack")
	public void destroyStack(IInventory target,
			@Arg(name = "slotNumber", description = "The slot number") Index slot)
	{
		IInventory inventory = InventoryUtils.getInventory(target);
		slot.checkElementIndex("slot id", inventory.getSizeInventory());
		inventory.setInventorySlotContents(slot.value, null);
		inventory.markDirty();
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get full stack information from id and/or damage")
	public ItemStack expandStack(IInventory target, @Arg(name = "stack") ItemStack itemStack) {
		return itemStack;
	}
}
