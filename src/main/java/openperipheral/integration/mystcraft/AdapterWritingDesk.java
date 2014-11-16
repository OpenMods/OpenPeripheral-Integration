package openperipheral.integration.mystcraft;

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
import openmods.utils.BlockUtils;
import openmods.utils.InventoryUtils;
import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class AdapterWritingDesk implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.tileentity.TileEntityDesk");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "mystcraft_desk";
	}

	@LuaCallable(description = "Get the maximum number of notebooks this desk can store", returnTypes = LuaReturnType.NUMBER)
	public int getMaxNotebookCount(Object tileEntityDesk) {
		return ReflectionHelper.call(tileEntityDesk, "getMaxNotebookCount");
	}

	@LuaCallable(description = "Get the name of a notebook", returnTypes = LuaReturnType.STRING)
	public String getNotebookName(Object desk, @Arg(name = "slot", description = "The writing desk slot you are interested in") int deskSlot) {
		return createInventoryWrapper(desk, deskSlot).getInventoryName();
	}

	@LuaCallable(description = "Get the number of pages in a notebook", returnTypes = LuaReturnType.NUMBER)
	public Integer getNotebookSize(Object desk, @Arg(name = "deskSlot") int deskSlot) {
		return createInventoryWrapper(desk, deskSlot).callOnNotebook("getItemCount");
	}

	@LuaCallable(description = "Get the contents of a slot in a notebook", returnTypes = LuaReturnType.NUMBER)
	public ItemStack getNotebookStackInSlot(Object desk, @Arg(name = "deskSlot") int deskSlot, @Arg(name = "notebookSlot") int notebookSlot) {
		return createInventoryWrapper(desk, deskSlot).getStackInSlot(notebookSlot - 1);
	}

	@LuaCallable(description = "Get the last slot index in a notebook", returnTypes = LuaReturnType.NUMBER)
	public Integer getLastNotebookSlot(Object desk, @Arg(name = "deskSlot") int deskSlot) {
		return createInventoryWrapper(desk, deskSlot).getSizeInventory() - 1;
	}

	@LuaCallable(description = "Swap notebook slots")
	public void swapNotebookPages(Object desk, @Arg(name = "deskSlot") int deskSlot, @Arg(name = "from") int from, @Arg(name = "to") int to) {
		InventoryUtils.swapStacks(createInventoryWrapper(desk, deskSlot), from - 1, to - 1);
	}

	@LuaCallable(
			returnTypes = LuaReturnType.NUMBER,
			description = "Push a page from the notebook into a specific slot in external inventory. Returns the amount of items moved")
	public int pushNotebookPage(
			Object desk,
			@Arg(name = "deskSlot") int deskSlot,
			@Arg(name = "direction", description = "The direction of the other inventory. (north, south, east, west, up or down)") ForgeDirection direction,
			@Arg(name = "fromSlot", description = "The page slot in inventory that you're pushing from") int fromSlot,
			@Optionals @Arg(name = "intoSlot", description = "The slot in the other inventory that you want to push into") Integer intoSlot) {
		IInventory source = createInventoryWrapper(desk, deskSlot);
		IInventory target = getTargetTile(desk, direction);

		return InventoryUtils.moveItemInto(source, fromSlot - 1, target, Objects.firstNonNull(intoSlot, 0) - 1, 64, direction.getOpposite(), true);
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Pull an item from the target inventory into any slot in the current one. Returns the amount of items moved")
	public int pullNotebookPage(Object desk,
			@Arg(name = "deskSlot") int deskSlot,
			@Arg(name = "direction", description = "The direction of the other inventory)") ForgeDirection direction,
			@Arg(name = "fromSlot", description = "The slot in the other inventory that you're pulling from") int notebookSlot) {
		IInventory source = getTargetTile(desk, direction);
		IInventory target = createInventoryWrapper(desk, deskSlot);
		return InventoryUtils.moveItemInto(source, notebookSlot - 1, target, -1, 1, direction.getOpposite(), true, false);
	}

	@LuaCallable(description = "Create a symbol page from the target symbol")
	public void writeSymbol(final TileEntity desk,
			@Arg(name = "deskSlot") int deskSlot,
			@Arg(name = "notebookSlot", description = "The source symbol to copy") int notebookSlot) {
		final String symbol = getSymbolFromPage(getNotebookStackInSlot(desk, deskSlot, notebookSlot));
		if (symbol != null) {
			FakePlayerPool.instance.executeOnPlayer((WorldServer)desk.getWorldObj(), new PlayerUser() {
				@Override
				public void usePlayer(OpenModsFakePlayer fakePlayer) {
					ReflectionHelper.call(CLASS, desk, "writeSymbol", ReflectionHelper.typed(fakePlayer, EntityPlayer.class), symbol);
				}
			});
		}
	}

	private static String getSymbolFromPage(ItemStack info) {
		if (info != null && info.hasTagCompound()) {
			Item item = info.getItem();
			if (item != null && "item.myst.page".equals(item.getUnlocalizedName())) {
				NBTTagCompound tag = info.getTagCompound();
				if (tag != null) { return tag.getString("symbol"); }
			}
		}
		return null;
	}

	private static IInventory getTargetTile(Object target, ForgeDirection direction) {
		Preconditions.checkArgument(direction != ForgeDirection.UNKNOWN, "Invalid direction");
		Preconditions.checkArgument(target instanceof TileEntity);
		TileEntity targetTile = BlockUtils.getTileInDirection((TileEntity)target, direction);
		Preconditions.checkArgument(targetTile instanceof IInventory, "Target direction is not a valid inventory");
		return (IInventory)targetTile;
	}

	private NotebookIInventoryWrapper createInventoryWrapper(Object tile, int number) {
		ItemStack notebook = ReflectionHelper.call(CLASS, tile, "getNotebook", ReflectionHelper.primitive((byte)(number - 1)));
		return new NotebookIInventoryWrapper(notebook);
	}
}
