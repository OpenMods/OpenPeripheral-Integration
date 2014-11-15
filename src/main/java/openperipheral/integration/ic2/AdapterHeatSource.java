package openperipheral.integration.ic2;

import ic2.api.energy.tile.IHeatSource;
import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;

public class AdapterHeatSource implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IHeatSource.class;
	}

	@Override
	public String getSourceId() {
		return "ic2_heat_source";
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public int getMaxKineticEnergy(IHeatSource target, @Arg(name = "side") ForgeDirection side) {
		return target.maxrequestHeatTick(side);
	}

}
