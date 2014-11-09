package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityBrewingStand;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;

public class AdapterBrewingStand implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityBrewingStand.class;
	}

	@LuaCallable
	public int getBrewTime(TileEntityBrewingStand brewingStand) {
		return brewingStand.getBrewTime();
	}

}
