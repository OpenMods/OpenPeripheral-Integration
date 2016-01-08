package openperipheral.integration.railcraft;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterTemperature implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.plugins.buildcraft.triggers.ITemperature");

	private final Function0<Float> GET_TEMPERATURE = MethodAccess.create(float.class, CLASS, "getTemperature");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_temperature";
	}

	@ScriptCallable(description = "Get the temperature", returnTypes = ReturnType.NUMBER)
	public float getTemperature(Object target) {
		return GET_TEMPERATURE.call(target);
	}
}
