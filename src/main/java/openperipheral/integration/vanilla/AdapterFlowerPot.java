package openperipheral.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFlowerPot;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterFlowerPot implements IPeripheralAdapter {

	@Override
	public String getSourceId() {
		return "flower_pot";
	}

	@Override
	public Class<?> getTargetClass() {
		return TileEntityFlowerPot.class;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE)
	public ItemStack getContents(TileEntityFlowerPot pot) {
		Item item = pot.getFlowerPotItem();

		if (item == null) return null;
		int data = pot.getFlowerPotData();
		return new ItemStack(item, 1, data);
	}
}
