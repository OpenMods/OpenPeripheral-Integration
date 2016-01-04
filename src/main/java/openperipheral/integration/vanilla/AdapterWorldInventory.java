package openperipheral.integration.vanilla;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import openmods.inventory.legacy.CustomSinks;
import openmods.inventory.legacy.ItemDistribution;
import openperipheral.api.adapter.IAdapterWithConstraints;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.IWorldPosProvider;
import openperipheral.api.adapter.method.*;
import openperipheral.api.helpers.Index;

import com.google.common.base.Preconditions;

// this is ugly. But all inventory handling functions need rewrite...
public class AdapterWorldInventory implements IPeripheralAdapter, IAdapterWithConstraints {

	private static final int ANY_SLOT = -1;
	private static final Index ANY_SLOT_INDEX = new Index(ANY_SLOT, 0);

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
					return te.getWorld();
				}

				@Override
				public BlockPos getPos() {
					return te.getPos();
				}
			};
		}

		throw new IllegalArgumentException("Invalid target object " + String.valueOf(target));
	}

	private static TileEntity getNeighborTarget(IInventory target, EnumFacing direction) {
		Preconditions.checkNotNull(direction, "Invalid direction");
		final IWorldPosProvider provider = getProvider(target);

		final World world = provider.getWorld();
		final BlockPos pos = provider.getPos().offset(direction);
		return world.getTileEntity(pos);
	}

	@Alias("pullItemIntoSlot")
	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Pull an item from a slot in another inventory into a slot in this one. Returns the amount of items moved")
	public int pullItem(IInventory target,
			@Arg(name = "direction", description = "The direction of the other inventory") EnumFacing direction,
			@Arg(name = "slot", description = "The slot in the OTHER inventory that you're pulling from") Index fromSlot,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to pull") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the current inventory that you want to pull into") Index intoSlot) {

		final TileEntity otherTarget = getNeighborTarget(target, direction);
		if (otherTarget == null) throw new IllegalArgumentException("Other block not found");
		if (!(otherTarget instanceof IInventory)) throw new IllegalArgumentException("Other block is not inventory");

		final IInventory otherInventory = (IInventory)otherTarget;
		if (otherTarget == target) return 0;
		if (maxAmount == null) maxAmount = 64;
		if (intoSlot == null) intoSlot = ANY_SLOT_INDEX;

		checkSlotId(otherInventory, fromSlot, "input");
		checkSlotId(target, intoSlot, "output");

		final int amount = ItemDistribution.moveItemInto(otherInventory, fromSlot.value, target, intoSlot.value, maxAmount, direction.getOpposite(), true);
		if (amount > 0) {
			target.markDirty();
			otherInventory.markDirty();
		}
		return amount;

	}

	@Alias("pushItemIntoSlot")
	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Push an item from the current inventory into pipe or slot on the other inventory. Returns the amount of items moved")
	public int pushItem(IInventory target,
			@Arg(name = "direction", description = "The direction of the other inventory") EnumFacing direction,
			@Arg(name = "slot", description = "The slot in the current inventory that you're pushing from") Index fromSlot,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to push") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the other inventory that you want to push into (ignored when target is pipe") Index intoSlot) {

		final TileEntity otherTarget = getNeighborTarget(target, direction);
		Preconditions.checkNotNull(otherTarget, "Other target not found");
		final IInventory thisInventory = target;

		if (maxAmount == null) maxAmount = 64;
		if (intoSlot == null) intoSlot = ANY_SLOT_INDEX;

		checkSlotId(thisInventory, fromSlot, "input");

		final int amount;
		if (otherTarget instanceof IInventory) {
			final IInventory otherInventory = (IInventory)otherTarget;
			checkSlotId(otherInventory, intoSlot, "output");
			amount = ItemDistribution.moveItemInto(thisInventory, fromSlot.value, otherInventory, intoSlot.value, maxAmount, direction, true);
			if (amount > 0) otherInventory.markDirty();
		} else {
			final CustomSinks.ICustomSink adapter = CustomSinks.createSink(otherTarget);
			if (adapter == null) throw new IllegalArgumentException("Invalid target");
			amount = ItemDistribution.moveItemInto(thisInventory, fromSlot.value, adapter, maxAmount, direction, true);
		}

		if (amount > 0) thisInventory.markDirty();
		return amount;
	}
}
