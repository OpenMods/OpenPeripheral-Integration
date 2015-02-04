package openperipheral.integration.ic2;

import ic2.api.energy.tile.IEnergySource;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

@Asynchronous
public class AdapterEnergySource implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergySource.class;
	}

	@Override
	public String getSourceId() {
		return "eu_source";
	}

	@ScriptCallable(description = "Get the EU output", returnTypes = ReturnType.NUMBER)
	public double getOfferedEnergy(IEnergySource source) {
		return source.getOfferedEnergy();
	}

	@ScriptCallable(description = "Determine the tier of this energy source (1 = LV, 2 = MV, 3 = HV, 4 = EV)", returnTypes = ReturnType.NUMBER)
	public int getEUSourceTier(IEnergySource sink) {
		return sink.getSourceTier();
	}
}
