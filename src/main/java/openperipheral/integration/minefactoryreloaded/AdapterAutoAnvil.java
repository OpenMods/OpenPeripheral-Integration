package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterAutoAnvil implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoAnvil");

	private final Function0<Boolean> GET_REPAIR_ONLY = MethodAccess.create(boolean.class, CLASS, "getRepairOnly");
	private final Function1<Void, Boolean> SET_REPAIR_ONLY = MethodAccess.create(void.class, CLASS, boolean.class, "setRepairOnly");

	@Override
	public String getSourceId() {
		return "mfr_anvil";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@ScriptCallable(description = "Get value of repair only toggle", returnTypes = ReturnType.BOOLEAN)
	public boolean getRepairOnly(Object tileEntityAutoAnvil) {
		return GET_REPAIR_ONLY.call(tileEntityAutoAnvil);
	}

	@ScriptCallable(description = "Set the value of repair only toggle")
	public void setRepairOnly(Object tileEntityAutoAnvil, @Arg(name = "repair") boolean repair) {
		// NOTE: This doesn't seem to always work as expected. Consulting Skyboy
		// about it.
		SET_REPAIR_ONLY.call(tileEntityAutoAnvil, repair);
	}

}
