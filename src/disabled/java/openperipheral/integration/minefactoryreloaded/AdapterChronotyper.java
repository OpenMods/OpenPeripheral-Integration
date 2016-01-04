package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterChronotyper implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityChronotyper");

	private final Function0<Boolean> GET_MOVE_ADULTS = MethodAccess.create(boolean.class, CLASS, "getMoveOld");
	private final Function1<Void, Boolean> SET_MOVE_ADULTS = MethodAccess.create(void.class, CLASS, boolean.class, "setMoveOld");

	@Override
	public String getSourceId() {
		return "mfr_chronotyper";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@ScriptCallable(description = "Should adults be moved?", returnTypes = ReturnType.BOOLEAN)
	public boolean getMoveAdults(Object tileEntityChronotyper) {
		return GET_MOVE_ADULTS.call(tileEntityChronotyper);
	}

	@ScriptCallable(description = "Set wheather adults should be moved")
	public void setMoveAdults(Object tileEntityChronotyper, @Arg(name = "adults") boolean adults) {
		SET_MOVE_ADULTS.call(tileEntityChronotyper, adults);
	}

}
