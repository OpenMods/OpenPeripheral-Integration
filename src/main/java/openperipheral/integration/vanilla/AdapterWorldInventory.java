package openperipheral.integration.vanilla;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.inventory.legacy.ItemDistribution;
import openmods.utils.InventoryUtils;
import openperipheral.api.adapter.IAdapterWithConstraints;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.IWorldPosProvider;
import openperipheral.api.adapter.method.*;
import openperipheral.api.helpers.Index;

import com.google.common.base.Preconditions;

public class AdapterWorldInventory implements IPeripheralAdapter, IAdapterWithConstraints {

	private static final int ANY_SLOT = -1;

	@Override
	public boolean canApply(Class<?> target) {
		return TileEntity.class.isAssignableFrom(target) ||
				IWorldPosProvider.class.isAssignableFrom(target);

	}

	@Override
	public Class<?> getTargetClass() {
		return IInventory.class;
	}

	@Override
	public String getSourceId() {
		return "inventory-world";
	}

	private static void checkSlotId(IInventory inventory, Index slot, String name) {
		Preconditions.checkNotNull(inventory, "Invalid inventory");
		if (slot.value != ANY_SLOT) slot.checkElementIndex(name + " slot id", inventory.getSizeInventory());
	}

	private static IWorldPosProvider getProvider(IInventory target) {
		if (target instanceof IWorldPosProvider) return (IWorldPosProvider)target;
		if (target instanceof TileEntity) {
			final TileEntity te = (TileEntity)target;
			return new IWorldPosProvider() {

				@Override
				public boolean isValid() {
					return !te.isInvalid();
				}

				@Override
				public World getWorld() {
					return te.getWorldObj();
				}

				@Override
				public int getX() {
					return te.xCoord;
				}

				@Override
				public int getY() {
					return te.yCoord;
				}

				@Override
				public int getZ() {
					return te.zCoord;
				}
			};
		}

		throw new IllegalArgumentException("Invalid target object " + String.valueOf(target));
	}

	private static IInventory getNeighborInventory(IInventory target, ForgeDirection direction) {
		Preconditions.checkNotNull(direction, "Invalid direction");
		Preconditions.checkArgument(direction != ForgeDirection.UNKNOWN, "Invalid direction");
		final IWorldPosProvider provider = getProvider(target);

		return InventoryUtils.getInventory(provider.getWorld(), provider.getX(), provider.getY(), provider.getZ(), direction);
	}

	@Alias("pullItemIntoSlot")
	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Pull an item from a slot in another inventory into a slot in this one. Returns the amount of items moved")
	public int pullItem(IInventory target,
			@Arg(name = "direction", description = "The direction of the other inventory") ForgeDirection direction,
			@Arg(name = "slot", description = "The slot in the OTHER inventory that you're pulling from") Index fromSlot,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to pull") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the current inventory that you want to pull into") Index intoSlot) {

		final IInventory otherInventory = getNeighborInventory(target, direction);
		Preconditions.checkNotNull(otherInventory, "Other inventory not found");
		final IInventory thisInventory = InventoryUtils.getInventory(target);

		if (otherInventory == target) return 0;
		if (maxAmount == null) maxAmount = 64;
		if (intoSlot == null) intoSlot = new Index(ANY_SLOT);

		checkSlotId(otherInventory, fromSlot, "input");
		checkSlotId(thisInventory, intoSlot, "output");

		final int amount = ItemDistribution.moveItemInto(otherInventory, fromSlot.value, thisInventory, intoSlot.value, maxAmount, direction.getOpposite(), true);
		if (amount > 0) {
			thisInventory.markDirty();
			otherInventory.markDirty();
		}
		return amount;
	}

	@Alias("pushItemIntoSlot")
	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Push an item from the current inventory into slot on the other one. Returns the amount of items moved")
	public int pushItem(IInventory target,
			@Arg(name = "direction", description = "The direction of the other inventory") ForgeDirection direction,
			@Arg(name = "slot", description = "The slot in the current inventory that you're pushing from") Index fromSlot,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to push") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the other inventory that you want to push into") Index intoSlot) {

		final IInventory otherInventory = getNeighborInventory(target, direction);
		Preconditions.checkNotNull(otherInventory, "Other inventory not found");
		final IInventory thisInventory = InventoryUtils.getInventory(target);

		if (otherInventory == target) return 0;
		if (maxAmount == null) maxAmount = 64;
		if (intoSlot == null) intoSlot = new Index(ANY_SLOT);

		checkSlotId(thisInventory, fromSlot, "input");
		checkSlotId(otherInventory, intoSlot, "output");

		int amount = ItemDistribution.moveItemInto(thisInventory, fromSlot.value, otherInventory, intoSlot.value, maxAmount, direction, true);
		if (amount > 0) {
			thisInventory.markDirty();
			otherInventory.markDirty();
		}
		return amount;
	}
}
