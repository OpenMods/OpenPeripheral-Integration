package openperipheral.integration.ic2;

import ic2.api.energy.tile.IEnergySource;
import openperipheral.api.*;

@Asynchronous
public class AdapterEnergySource implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergySource.class;
	}

	@LuaCallable(description = "Get the EU output", returnTypes = LuaReturnType.NUMBER)
	public double getOfferedEnergy(IEnergySource source) {
		return source.getOfferedEnergy();
	}

	@LuaCallable(description = "Determine the tier of this energy source (1 = LV, 2 = MV, 3 = HV, 4 = EV)", returnTypes = LuaReturnType.NUMBER)
	public int getEUSourceTier(IEnergySource sink) {
		return sink.getSourceTier();
	}
}
