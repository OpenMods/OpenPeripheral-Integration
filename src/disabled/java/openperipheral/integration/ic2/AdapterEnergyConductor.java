package openperipheral.integration.ic2;

import ic2.api.energy.tile.IEnergyConductor;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

@Asynchronous
public class AdapterEnergyConductor implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyConductor.class;
	}

	@Override
	public String getSourceId() {
		return "eu_conductor";
	}

	@ScriptCallable(description = "Get the EU conduction loss", returnTypes = ReturnType.NUMBER)
	public double getEUConductionLoss(IEnergyConductor conductor) {
		return conductor.getConductionLoss();
	}

	@ScriptCallable(description = "Get the EU conductor breakdown energy", returnTypes = ReturnType.NUMBER)
	public double getEUConductorBreakdownEnergy(IEnergyConductor conductor) {
		return conductor.getConductorBreakdownEnergy();
	}

	@ScriptCallable(description = "Get the EU insulation breakdown energy", returnTypes = ReturnType.NUMBER)
	public double getEUInsulationBreakdownEnergy(IEnergyConductor conductor) {
		return conductor.getInsulationBreakdownEnergy();
	}

	@ScriptCallable(description = "Get the EU insulation energy absorption", returnTypes = ReturnType.NUMBER)
	public double getEUInsulationEnergyAbsorption(IEnergyConductor conductor) {
		return conductor.getInsulationEnergyAbsorption();
	}

}
