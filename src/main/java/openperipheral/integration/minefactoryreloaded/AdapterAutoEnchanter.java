package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openperipheral.api.*;

public class AdapterAutoEnchanter implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoEnchanter");

	private final Function0<Integer> GET_TARGET_LEVEL = MethodAccess.create(int.class, CLASS, "getTargetLevel");
	private final Function1<Void, Integer> SET_TARGET_LEVEL = MethodAccess.create(void.class, CLASS, int.class, "setTargetLevel");

	@Override
	public String getSourceId() {
		return "mfr_enchanter";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@LuaCallable(description = "Get target level of enchantment", returnTypes = LuaReturnType.NUMBER)
	public int getTargetLevel(Object tileEntityAutoEnchanter) {
		return GET_TARGET_LEVEL.call(tileEntityAutoEnchanter);
	}

	@LuaCallable(description = "Set the target level of enchantment (1-30)")
	public void setTargetLevel(Object tileEntityAutoEnchanter, @Arg(name = "level") int level) {
		SET_TARGET_LEVEL.call(tileEntityAutoEnchanter, level);
	}
}
