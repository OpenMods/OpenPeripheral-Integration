package openperipheral.integration.railcraft;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterNeedsMaintenance implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.plugins.buildcraft.triggers.INeedsMaintenance");

	private final Function0<Boolean> NEEDS_MAINTENANCE = MethodAccess.create(boolean.class, CLASS, "needsMaintenance");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_needs_maintenance";
	}

	@ScriptCallable(description = "Returns true if needs maintenance", returnTypes = ReturnType.BOOLEAN)
	public boolean needsMaintenance(Object target) {
		return NEEDS_MAINTENANCE.call(target);
	}
}
