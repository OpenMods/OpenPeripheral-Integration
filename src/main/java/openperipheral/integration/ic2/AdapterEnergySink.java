package openperipheral.integration.ic2;

import ic2.api.energy.tile.IEnergySink;
import openperipheral.api.*;

@Asynchronous
public class AdapterEnergySink implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergySink.class;
	}

	@Override
	public String getSourceId() {
		return "eu_sink";
	}

	@LuaCallable(description = "Get the maximum safe EU input", returnTypes = LuaReturnType.NUMBER)
	public double getDemandedEnergy(IEnergySink sink) {
		return sink.getDemandedEnergy();
	}

	@LuaCallable(description = "Determine the tier of this energy sink (1 = LV, 2 = MV, 3 = HV, 4 = EV)", returnTypes = LuaReturnType.NUMBER)
	public int getEUSinkTier(IEnergySink sink) {
		return sink.getSinkTier();
	}

}
