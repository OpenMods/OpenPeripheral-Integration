package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openperipheral.api.*;

public class AdapterAutoDisenchanter implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoDisenchanter");

	private final Function0<Boolean> GET_REPEAT = MethodAccess.create(boolean.class, CLASS, "getRepeatDisenchant");
	private final Function1<Void, Boolean> SET_REPEAT = MethodAccess.create(void.class, CLASS, boolean.class, "setRepeatDisenchant");

	@Override
	public String getSourceId() {
		return "mfr_disenchanter";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@LuaCallable(description = "Get value of repeat disenchant toggle", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getRepeat(Object tileEntityAutoDisenchanter) {
		return GET_REPEAT.call(tileEntityAutoDisenchanter);
	}

	@LuaCallable(description = "Set the value of repeat disenchant toggle")
	public void setRepeat(Object tileEntityAutoDisenchanter, @Arg(name = "repeat") boolean repeat) {
		SET_REPEAT.call(tileEntityAutoDisenchanter, repeat);
	}

}
