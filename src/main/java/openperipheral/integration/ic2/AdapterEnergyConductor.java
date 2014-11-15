package openperipheral.integration.ic2;

import ic2.api.energy.tile.IEnergyConductor;
import openperipheral.api.*;

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

	@LuaCallable(description = "Get the EU conduction loss", returnTypes = LuaReturnType.NUMBER)
	public double getEUConductionLoss(IEnergyConductor conductor) {
		return conductor.getConductionLoss();
	}

	@LuaCallable(description = "Get the EU conductor breakdown energy", returnTypes = LuaReturnType.NUMBER)
	public double getEUConductorBreakdownEnergy(IEnergyConductor conductor) {
		return conductor.getConductorBreakdownEnergy();
	}

	@LuaCallable(description = "Get the EU insulation breakdown energy", returnTypes = LuaReturnType.NUMBER)
	public double getEUInsulationBreakdownEnergy(IEnergyConductor conductor) {
		return conductor.getInsulationBreakdownEnergy();
	}

	@LuaCallable(description = "Get the EU insulation energy absorption", returnTypes = LuaReturnType.NUMBER)
	public double getEUInsulationEnergyAbsorption(IEnergyConductor conductor) {
		return conductor.getInsulationEnergyAbsorption();
	}

}
