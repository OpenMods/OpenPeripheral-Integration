package openperipheral.integration.railcraft;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterHasWork implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.plugins.buildcraft.triggers.IHasWork");

	private final Function0<Boolean> HAS_WORK = MethodAccess.create(boolean.class, CLASS, "hasWork");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_has_work";
	}

	@ScriptCallable(description = "Returns true if has work", returnTypes = ReturnType.BOOLEAN)
	public boolean hasWork(Object target) {
		return HAS_WORK.call(target);
	}
}
