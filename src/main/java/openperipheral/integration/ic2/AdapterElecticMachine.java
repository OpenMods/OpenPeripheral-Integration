package openperipheral.integration.ic2;

import openmods.utils.FieldAccess;
import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

@Asynchronous
public class AdapterElecticMachine implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("ic2.core.block.machine.tileentity.TileEntityElectricMachine");

	private final FieldAccess<Integer> MAX_ENERGY = FieldAccess.create(CLASS, "maxEnergy");
	private final FieldAccess<Double> ENERGY = FieldAccess.create(CLASS, "energy");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "ic2_machine";
	}

	@LuaCallable(description = "Get the current progress as a percentage", returnTypes = LuaReturnType.NUMBER)
	public double getProgress(Object massfab) {
		double energy = ENERGY.get(massfab);
		int maxEnergy = MAX_ENERGY.get(massfab);
		return Math.min(energy / maxEnergy, 1) * 100;
	}

}
