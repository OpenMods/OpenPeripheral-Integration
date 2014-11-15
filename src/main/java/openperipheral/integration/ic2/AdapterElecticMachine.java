package openperipheral.integration.ic2;

import openmods.utils.FieldAccess;
import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

@Asynchronous
public class AdapterElecticMachine implements IPeripheralAdapter {
	private static final Class<?> CLAZZ = ReflectionHelper.getClass("ic2.core.block.machine.tileentity.TileEntityElectricMachine");

	private static final FieldAccess<Integer> maxEnergyAccess = FieldAccess.create(CLAZZ, "maxEnergy");
	private static final FieldAccess<Double> energyAccess = FieldAccess.create(CLAZZ, "energy");

	@Override
	public Class<?> getTargetClass() {
		return CLAZZ;
	}

	@Override
	public String getSourceId() {
		return "ic2_machine";
	}

	@LuaCallable(description = "Get the current progress as a percentage", returnTypes = LuaReturnType.NUMBER)
	public double getProgress(Object massfab) {
		double energy = energyAccess.get(massfab);
		int maxEnergy = maxEnergyAccess.get(massfab);
		return Math.min(energy / maxEnergy, 1) * 100;
	}

}
