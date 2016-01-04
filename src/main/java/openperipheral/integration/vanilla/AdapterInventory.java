package openperipheral.integration.vanilla;

import java.util.List;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
		return target.getName();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the size of this inventory")
	public int getInventorySize(IInventory target) {
		return target.getSizeInventory();
	}

	@Asynchronous(false)
	@ScriptCallable(description = "Condense and tidy the stacks in an inventory")
	public void condenseItems(IInventory target) {
		List<ItemStack> stacks = Lists.newArrayList();

		for (int i = 0; i < target.getSizeInventory(); i++) {
			ItemStack sta = target.getStackInSlot(i);
			if (sta != null) stacks.add(sta.copy());
			target.setInventorySlotContents(i, null);
		}

		for (ItemStack stack : stacks)
			ItemDistribution.insertItemIntoInventory(target, stack);

		target.markDirty();
	}

	@Asynchronous(false)
	@ScriptCallable(description = "Swap two slots in the inventory")
	public void swapStacks(IInventory target,
			@Arg(name = "from", description = "The first slot") Index fromSlot,
			@Arg(name = "to", description = "The other slot") Index intoSlot,
			@Optionals @Arg(name = "fromDirection") EnumFacing fromDirection,
			@Arg(name = "fromDirection") EnumFacing toDirection) {
		final int size = target.getSizeInventory();
		fromSlot.checkElementIndex("first slot id", size);
		intoSlot.checkElementIndex("second slot id", size);
		if (target instanceof ISidedInventory) {
			Preconditions.checkNotNull(fromDirection, "Inventory is sided, but no source direction given");
			InventoryUtils.swapStacks((ISidedInventory)target,
					fromSlot.value, fromDirection,
					intoSlot.value, Objects.firstNonNull(toDirection, fromDirection));
		} else InventoryUtils.swapStacks(target, fromSlot.value, intoSlot.value);

		target.markDirty();
	}

	@ScriptCallable(returnTypes = ReturnType.OBJECT, description = "Get details of an item in a particular slot")
	public Object getStackInSlot(IInventory target,
			@Arg(name = "slotNumber", description = "Slot number") Index slot,
			@Optionals @Arg(name = "proxy", description = "If true, method will return proxy instead of computing whole table") Boolean proxy)
	{
		slot.checkElementIndex("slot id", target.getSizeInventory());
		ItemStack stack = target.getStackInSlot(slot.value);
		return proxy == Boolean.TRUE? OpcAccess.itemStackMetaBuilder.createProxy(stack) : stack;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get a table with all the items of the chest")
	public Map<Index, Object> getAllStacks(IInventory target,
			@Env(Constants.ARG_ARCHITECTURE) IArchitecture access,
			@Optionals @Arg(name = "proxy", description = "If false, method will compute whole table, instead of returning proxy") Boolean proxy) {
		Map<Index, Object> result = Maps.newHashMap();

		for (int i = 0; i < target.getSizeInventory(); i++) {
			ItemStack stack = target.getStackInSlot(i);
			if (stack != null) result.put(access.createIndex(i), (proxy != Boolean.FALSE)? OpcAccess.itemStackMetaBuilder.createProxy(stack) : stack);
		}
		return result;
	}

	@Asynchronous(false)
	@ScriptCallable(description = "Destroy a stack")
	public void destroyStack(IInventory target,
			@Arg(name = "slotNumber", description = "The slot number") Index slot)
	{
		slot.checkElementIndex("slot id", target.getSizeInventory());
		target.setInventorySlotContents(slot.value, null);
		target.markDirty();
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get full stack information from id and/or damage")
	public ItemStack expandStack(IInventory target, @Arg(name = "stack") ItemStack itemStack) {
		return itemStack;
	}
}
