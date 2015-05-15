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

	private static void checkSlotId(IInventory inventory, int slot, String name) {
		Preconditions.checkNotNull(inventory, "Invalid inventory");
		if (slot != ANY_SLOT) Preconditions.checkElementIndex(slot, inventory.getSizeInventory(), name + " slot id");
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
			@Arg(name = "direction", description = "The direction of the other inventory. (north, south, east, west, up or down)") ForgeDirection direction,
			@Arg(name = "slot", description = "The slot in the OTHER inventory that you're pulling from") int fromSlot,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to pull") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the current inventory that you want to pull into") Integer intoSlot) {

		final IInventory otherInventory = getNeighborInventory(target, direction);
		Preconditions.checkNotNull(otherInventory, "Other inventory not found");
		final IInventory thisInventory = InventoryUtils.getInventory(target);

		if (otherInventory == target) return 0;
		if (maxAmount == null) maxAmount = 64;
		if (intoSlot == null) intoSlot = 0;

		fromSlot -= 1;
		intoSlot -= 1;

		checkSlotId(otherInventory, fromSlot, "input");
		checkSlotId(thisInventory, intoSlot, "output");

		final int amount = ItemDistribution.moveItemInto(otherInventory, fromSlot, thisInventory, intoSlot, maxAmount, direction.getOpposite(), true);
		if (amount > 0) {
			thisInventory.markDirty();
			otherInventory.markDirty();
		}

		return amount;
	}

	@Alias("pushItemIntoSlot")
	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Push an item from the current inventory into slot on the other one. Returns the amount of items moved")
	public int pushItem(IInventory target,
			@Arg(name = "direction", description = "The direction of the other inventory. (north, south, east, west, up or down)") ForgeDirection direction,
			@Arg(name = "slot", description = "The slot in the current inventory that you're pushing from") int fromSlot,
			@Optionals @Arg(name = "maxAmount", description = "The maximum amount of items you want to push") Integer maxAmount,
			@Arg(name = "intoSlot", description = "The slot in the other inventory that you want to push into") Integer intoSlot) {

		final IInventory otherInventory = getNeighborInventory(target, direction);
		Preconditions.checkNotNull(otherInventory, "Other inventory not found");
		final IInventory thisInventory = InventoryUtils.getInventory(target);

		if (otherInventory == target) return 0;
		if (maxAmount == null) maxAmount = 64;
		if (intoSlot == null) intoSlot = 0;

		fromSlot -= 1;
		intoSlot -= 1;

		checkSlotId(thisInventory, fromSlot, "input");
		checkSlotId(otherInventory, intoSlot, "output");

		int amount = ItemDistribution.moveItemInto(thisInventory, fromSlot, otherInventory, intoSlot, maxAmount, direction, true);
		if (amount > 0) {
			thisInventory.markDirty();
			otherInventory.markDirty();
		}

		return amount;
	}
}
