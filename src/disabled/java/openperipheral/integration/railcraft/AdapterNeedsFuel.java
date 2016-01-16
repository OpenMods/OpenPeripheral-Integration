package openperipheral.integration.railcraft;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterNeedsFuel implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.plugins.buildcraft.triggers.INeedsFuel");

	private final Function0<Boolean> NEEDS_FUEL = MethodAccess.create(boolean.class, CLASS, "needsFuel");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_needs_fuel";
	}

	@ScriptCallable(description = "Returns true if needs fuel", returnTypes = ReturnType.BOOLEAN)
	public boolean needsFuel(Object target) {
		return NEEDS_FUEL.call(target);
	}
}
