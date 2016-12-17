package openperipheral.integration.mystcraft.v2;

import com.google.common.base.Preconditions;
import com.xcompwiz.mystcraft.api.item.IItemPageCollection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import openmods.fakeplayer.FakePlayerPool;
import openmods.fakeplayer.FakePlayerPool.PlayerUser;
import openmods.fakeplayer.OpenModsFakePlayer;
import openmods.inventory.GenericInventory;
import openmods.inventory.legacy.ItemDistribution;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.MethodAccess.Function2;
import openmods.reflection.ReflectionHelper;
import openmods.utils.BlockUtils;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.Optionals;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.api.helpers.Index;

public class AdapterWritingDesk implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.tileentity.TileEntityDesk");

	private final Function0<Integer> GET_MAX_NOTEBOOK_COUNT = MethodAccess.create(int.class, CLASS, "getMaxSurfaceTabCount");
	private final Function1<ItemStack, Byte> GET_NOTEBOOK = MethodAccess.create(ItemStack.class, CLASS, byte.class, "getTabItem");
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

	@ScriptCallable(description = "Get notebook wrapper", returnTypes = ReturnType.OBJECT)
	public NotebookWrapper getNotebook(TileEntity desk, @Arg(name = "slot", description = "The writing desk slot you are interested in") Index deskSlot) {
		return createInventoryWrapper(desk, deskSlot);
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
		final NotebookWrapper wrapper = createInventoryWrapper(desk, deskSlot);

		ItemStack page = wrapper.getPageFromSlot(fromSlot);

		ItemStack removedPage = wrapper.removePage(page);
		Preconditions.checkNotNull(removedPage, "No page in notebook");

		GenericInventory tmp = new GenericInventory("tmp", false, 1);
		tmp.setInventorySlotContents(0, removedPage.copy());

		final IInventory target = getTargetTile(desk, direction);

		if (intoSlot == null) intoSlot = Index.fromJava(-1, 0);

		int result = ItemDistribution.moveItemInto(tmp, 0, target, intoSlot.value, removedPage.stackSize, direction, true);

		int remains = removedPage.stackSize - result;
		if (remains >= 0) {
			ItemStack returns = removedPage.copy();
			returns.stackSize = remains;
			wrapper.addPage(returns);
		}

		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Pull an item from the target inventory into any slot in the current one. Returns true if item was consumed")
	public boolean pullNotebookPage(TileEntity desk,
			@Arg(name = "deskSlot") Index deskSlot,
			@Arg(name = "direction", description = "The direction of the other inventory)") ForgeDirection direction,
			@Arg(name = "fromSlot", description = "The slot in the other inventory that you're pulling from") Index fromSlot) {

		IInventory inv = getTargetTile(desk, direction);

		fromSlot.checkElementIndex("input slot", inv.getSizeInventory());

		ItemStack stack = inv.getStackInSlot(fromSlot.value);
		Preconditions.checkNotNull(stack, "Other inventory empty");

		final NotebookWrapper wrapper = createInventoryWrapper(desk, deskSlot);

		ItemStack added = wrapper.addPage(stack);
		inv.setInventorySlotContents(fromSlot.value, added);
		inv.markDirty();
		return added == null;
	}

	@ScriptCallable(description = "Create a symbol page from the target symbol")
	public void writeSymbol(final TileEntity desk,
			@Arg(name = "deskSlot") Index deskSlot,
			@Arg(name = "notebookSlot", description = "The source symbol to copy") Index notebookSlot) {
		Preconditions.checkNotNull(MystcraftAccess.pageApi, "Functionality not available");

		final NotebookWrapper wrapper = createInventoryWrapper(desk, deskSlot);
		ItemStack page = wrapper.getPageFromSlot(notebookSlot);
		final String symbol = MystcraftAccess.pageApi.getPageSymbol(page);
		if (symbol != null) {
			FakePlayerPool.instance.executeOnPlayer((WorldServer)desk.getWorldObj(), new PlayerUser() {
				@Override
				public void usePlayer(OpenModsFakePlayer fakePlayer) {
					WRITE_SYMBOL.call(desk, fakePlayer, symbol);
				}
			});
		}
	}

	private static IInventory getTargetTile(TileEntity target, ForgeDirection direction) {
		Preconditions.checkArgument(direction != ForgeDirection.UNKNOWN, "Invalid direction");
		TileEntity targetTile = BlockUtils.getTileInDirection(target, direction);
		Preconditions.checkArgument(targetTile instanceof IInventory, "Target direction is not a valid inventory");
		return (IInventory)targetTile;
	}

	private NotebookWrapper createInventoryWrapper(TileEntity tile, Index slot) {
		ItemStack notebook = GET_NOTEBOOK.call(tile, slot.byteValue());
		Preconditions.checkState(notebook != null, "Empty slot");
		Item item = notebook.getItem();
		Preconditions.checkState(item instanceof IItemPageCollection, "Invalid item in slot");
		return new NotebookWrapper((WorldServer)tile.getWorldObj(), (IItemPageCollection)item, notebook);
	}
}
