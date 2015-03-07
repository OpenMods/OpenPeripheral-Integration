package openperipheral.integration.vanilla;

import java.util.List;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.inventory.legacy.ItemDistribution;
import openmods.utils.InventoryUtils;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;
import openperipheral.api.meta.IItemStackPartialMetaBuilder;

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

	@ScriptCallable(description = "Swap two slots in the inventory")
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

	@ScriptCallable(returnTypes = ReturnType.OBJECT, description = "Get details of an item in a particular slot")
	public Object getStackInSlot(IInventory target,
			@Arg(name = "slotNumber", description = "The slot number, from 1 to the max amount of slots") int slot,
			@Optionals @Arg(name = "proxy", description = "If true, method will return proxy instead of computing whole table") Boolean proxy)
	{
		IInventory inventory = InventoryUtils.getInventory(target);
		slot -= 1;
		Preconditions.checkElementIndex(slot, inventory.getSizeInventory(), "slot id");
		ItemStack stack = inventory.getStackInSlot(slot);
		return proxy == Boolean.TRUE? ApiAccess.getApi(IItemStackPartialMetaBuilder.class).createProxy(stack) : stack;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get a table with all the items of the chest")
	public Map<Integer, Object> getAllStacks(IInventory target,
			@Optionals @Arg(name = "proxy", description = "If false, method will compute whole table, instead of returning proxy") Boolean proxy) {
		final IInventory inventory = InventoryUtils.getInventory(target);
		Map<Integer, Object> result = Maps.newHashMap();

		final IItemStackPartialMetaBuilder builder = ApiAccess.getApi(IItemStackPartialMetaBuilder.class);
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) result.put(i + 1, (proxy != Boolean.FALSE)? builder.createProxy(stack) : stack);
		}
		return result;
	}

	@ScriptCallable(description = "Destroy a stack")
	public void destroyStack(IInventory target,
			@Arg(name = "slotNumber", description = "The slot number, from 1 to the max amount of slots") int slot)
	{
		IInventory inventory = InventoryUtils.getInventory(target);
		slot -= 1;
		Preconditions.checkElementIndex(slot, inventory.getSizeInventory(), "slot id");
		inventory.setInventorySlotContents(slot, null);
		inventory.markDirty();
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get full stack information from stub one {id=..., [qty=...], [dmg=...]}")
	public ItemStack expandStack(IInventory target, @Arg(name = "stack", type = ArgType.TABLE) ItemStack itemStack) {
		return itemStack;
	}
}
