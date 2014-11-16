package openperipheral.integration.thaumcraft;

import openmods.reflection.FieldAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.*;

@Asynchronous
public class AdapterBrainJar implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileJarBrain");

	private final FieldAccess<Integer> XP = FieldAccess.create(CLASS, "xp");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_brain";
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Returns the amount of XP stored in the Brain in a Jar")
	public int getXP(Object target) {
		return XP.get(target);
	}
}
