package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterAutoEnchanter implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoEnchanter");

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
		return ReflectionHelper.call(tileEntityAutoEnchanter, "getTargetLevel");
	}

	@LuaCallable(description = "Set the target level of enchantment (1-30)")
	public void setTargetLevel(Object tileEntityAutoEnchanter, @Arg(name = "level") int level) {
		ReflectionHelper.call(tileEntityAutoEnchanter, "setTargetLevel", ReflectionHelper.primitive(level));
	}

}
