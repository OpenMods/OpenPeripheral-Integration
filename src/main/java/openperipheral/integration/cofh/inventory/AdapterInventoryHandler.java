package openperipheral.integration.cofh.inventory;

import cofh.api.inventory.IInventoryHandler;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterInventoryHandler implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IInventoryHandler.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_inventory";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the contents of the IInventoryHandler's inventory")
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
