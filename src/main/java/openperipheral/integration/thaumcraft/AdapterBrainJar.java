package openperipheral.integration.thaumcraft;

import openmods.utils.FieldAccess;
import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

@Asynchronous
public class AdapterBrainJar implements IPeripheralAdapter {
	private static final Class<?> TILE_JAR_BRAIN_CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileJarBrain");

	private static final FieldAccess<Integer> XP_ACCESS = FieldAccess.create(TILE_JAR_BRAIN_CLASS, "xp");

	@Override
	public Class<?> getTargetClass() {
		return TILE_JAR_BRAIN_CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_brain";
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Returns the amount of XP stored in the Brain in a Jar")
	public int getXP(Object target) {
		return XP_ACCESS.get(target);
	}
}
