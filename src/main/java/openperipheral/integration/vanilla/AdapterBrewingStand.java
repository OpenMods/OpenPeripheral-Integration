package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityBrewingStand;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterBrewingStand implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityBrewingStand.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_brewing";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getBrewTime(TileEntityBrewingStand brewingStand) {
		return brewingStand.getBrewTime();
	}

}
