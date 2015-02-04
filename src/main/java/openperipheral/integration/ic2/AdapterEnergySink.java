package openperipheral.integration.ic2;

import ic2.api.energy.tile.IEnergySink;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

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

	@ScriptCallable(description = "Get the maximum safe EU input", returnTypes = ReturnType.NUMBER)
	public double getDemandedEnergy(IEnergySink sink) {
		return sink.getDemandedEnergy();
	}

	@ScriptCallable(description = "Determine the tier of this energy sink (1 = LV, 2 = MV, 3 = HV, 4 = EV)", returnTypes = ReturnType.NUMBER)
	public int getEUSinkTier(IEnergySink sink) {
		return sink.getSinkTier();
	}

}
