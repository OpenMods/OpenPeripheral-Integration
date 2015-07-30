package openperipheral.integration.mystcraft.v1;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.fakeplayer.FakePlayerPool;
import openmods.fakeplayer.FakePlayerPool.PlayerUser;
import openmods.fakeplayer.OpenModsFakePlayer;
import openmods.inventory.legacy.ItemDistribution;
import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.MethodAccess.Function2;
import openmods.utils.BlockUtils;
import openmods.utils.InventoryUtils;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;
import openperipheral.api.helpers.Index;

import com.google.common.base.Preconditions;

public class AdapterWritingDesk implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.tileentity.TileEntityDesk");

	private final Function0<Integer> GET_MAX_NOTEBOOK_COUNT = MethodAccess.create(int.class, CLASS, "getMaxNotebookCount");
	private final Function1<ItemStack, Byte> GET_NOTEBOOK = MethodAccess.create(ItemStack.class, CLASS, byte.class, "getNotebook");
	private final Function2<Void, EntityPlayer, String> WRITE_SYMBOL = MethodAccess.create(void.class, CLASS, EntityPlayer.class, String.class, "writeSymbol");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "mystcraft_desk";
	}

	@ScriptCallable(description = "Get the maximum number of notebooks this desk can store", returnTypes = ReturnType.NUMBER)
	public int getMaxNotebookCount(Object tileEntityDesk) {
		return GET_MAX_NOTEBOOK_COUNT.call(tileEntityDesk);
	}

	@ScriptCallable(description = "Get the name of a notebook", returnTypes = ReturnType.STRING)
	public String getNotebookName(Object desk, @Arg(name = "slot", description = "The writing desk slot you are interested in") Index deskSlot) {
		return createInventoryWrapper(desk, deskSlot).getInventoryName();
	}

	@ScriptCallable(description = "Get the number of pages in a notebook", returnTypes = ReturnType.NUMBER)
	public Integer getNotebookSize(Object desk, @Arg(name = "deskSlot") Index deskSlot) {
		return createInventoryWrapper(desk, deskSlot).getItemCount();
	}

	@ScriptCallable(description = "Get the contents of a slot in a notebook", returnTypes = ReturnType.NUMBER)
	public ItemStack getNotebookStackInSlot(Object desk, @Arg(name = "deskSlot") Index deskSlot, @Arg(name = "notebookSlot") Index notebookSlot) {
		return createInventoryWrapper(desk, deskSlot).getStackInSlot(notebookSlot.value);
	}

	@ScriptCallable(description = "Get the last slot index in a notebook", returnTypes = ReturnType.NUMBER)
	public Integer getLastNotebookSlot(Object desk, @Arg(name = "deskSlot") Index deskSlot) {
		return createInventoryWrapper(desk, deskSlot).getSizeInventory() - 1;
	}

	@ScriptCallable(description = "Swap notebook slots")
	public void swapNotebookPages(Object desk, @Arg(name = "deskSlot") Index deskSlot, @Arg(name = "from") Index from, @Arg(name = "to") Index to) {
		InventoryUtils.swapStacks(createInventoryWrapper(desk, deskSlot), from.value, to.value);
	}

	@ScriptCallable(
			returnTypes = ReturnType.NUMBER,
			description = "Push a page from the notebook into a specific slot in external inventory. Returns the amount of items moved")
	public int pushNotebookPage(
			TileEntity desk,
			@Arg(name = "deskSlot") Index deskSlot,
			@Arg(name = "direction", description = "The direction of the other inventory. (north, south, east, west, up or down)") ForgeDirection direction,
			@Arg(name = "fromSlot", description = "The page slot in inventory that you're pushing from") Index fromSlot,
			@Optionals @Arg(name = "intoSlot", description = "The slot in the other inventory that you want to push into") Index intoSlot) {
		IInventory source = createInventoryWrapper(desk, deskSlot);
		IInventory target = getTargetTile(desk, direction);

		if (intoSlot == null) intoSlot = new Index(-1, 0);

		final int amount = ItemDistribution.moveItemInto(source, fromSlot.value, target, intoSlot.value, 64, direction.getOpposite(), true);
		if (amount > 0) target.markDirty();
		return amount;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Pull an item from the target inventory into any slot in the current one. Returns the amount of items moved")
	public int pullNotebookPage(TileEntity desk,
			@Arg(name = "deskSlot") Index deskSlot,
			@Arg(name = "direction", description = "The direction of the other inventory)") ForgeDirection direction,
			@Arg(name = "fromSlot", description = "The slot in the other inventory that you're pulling from") int notebookSlot) {
		IInventory source = getTargetTile(desk, direction);
		IInventory target = createInventoryWrapper(desk, deskSlot);
		final int amount = ItemDistribution.moveItemInto(source, notebookSlot - 1, target, -1, 1, direction.getOpposite(), true, false);
		if (amount > 0) source.markDirty();
		return amount;
	}

	@ScriptCallable(description = "Create a symbol page from the target symbol")
	public void writeSymbol(final TileEntity desk,
			@Arg(name = "deskSlot") Index deskSlot,
			@Arg(name = "notebookSlot", description = "The source symbol to copy") Index notebookSlot) {
		final String symbol = getSymbolFromPage(getNotebookStackInSlot(desk, deskSlot, notebookSlot));
		if (symbol != null) {
			FakePlayerPool.instance.executeOnPlayer((WorldServer)desk.getWorldObj(), new PlayerUser() {
				@Override
				public void usePlayer(OpenModsFakePlayer fakePlayer) {
					WRITE_SYMBOL.call(desk, fakePlayer, symbol);
				}
			});
		}
	}

	private static String getSymbolFromPage(ItemStack info) {
		if (info != null && info.hasTagCompound()) {
			Item item = info.getItem();
			if (item != null && "item.myst.page".equals(item.getUnlocalizedName())) {
				NBTTagCompound tag = info.getTagCompound();
				if (tag != null) return tag.getString("symbol");
			}
		}
		return null;
	}

	private static IInventory getTargetTile(TileEntity target, ForgeDirection direction) {
		Preconditions.checkArgument(direction != ForgeDirection.UNKNOWN, "Invalid direction");
		TileEntity targetTile = BlockUtils.getTileInDirection(target, direction);
		Preconditions.checkArgument(targetTile instanceof IInventory, "Target direction is not a valid inventory");
		return (IInventory)targetTile;
	}

	private NotebookIInventoryWrapper createInventoryWrapper(Object tile, Index slot) {
		ItemStack notebook = GET_NOTEBOOK.call(tile, slot.byteValue());
		return new NotebookIInventoryWrapper(notebook);
	}
}
