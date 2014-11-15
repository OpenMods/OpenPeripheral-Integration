package openperipheral.integration.cofh.inventory;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;
import cofh.api.inventory.IInventoryHandler;

public class AdapterInventoryHandler implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IInventoryHandler.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_inventory";
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Get the contents of the IInventoryHandler's inventory")
	List<ItemStack> getInventoryContents(IInventoryHandler target,
			@Arg(name = "from") ForgeDirection from) {
		return target.getInventoryContents(from);
	}

	int getSizeInventory(IInventoryHandler target,
			@Arg(name = "from") ForgeDirection from) {
		return target.getSizeInventory(from);
	}

	boolean isEmpty(IInventoryHandler target,
			@Arg(name = "from") ForgeDirection from) {
		return target.isEmpty(from);
	}

	boolean isFull(IInventoryHandler target,
			@Arg(name = "from") ForgeDirection from) {
		return target.isFull(from);
	}
}
