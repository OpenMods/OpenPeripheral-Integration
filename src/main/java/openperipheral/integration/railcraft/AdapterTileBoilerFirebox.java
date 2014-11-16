package openperipheral.integration.railcraft;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterTileBoilerFirebox implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.blocks.machine.beta.TileBoilerFirebox");

	private final Function0<Boolean> IS_BURNING = MethodAccess.create(boolean.class, CLASS, "isBurning");
	private final Function0<Float> GET_TEMPERATURE = MethodAccess.create(float.class, CLASS, "getTemperature");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_firebox";
	}

	@LuaCallable(description = "Get whether the boiler is active or not", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isBurning(Object target) {
		return IS_BURNING.call(target);
	}

	@LuaCallable(description = "Get the temperature of the boiler", returnTypes = LuaReturnType.NUMBER)
	public float getTemperature(Object target) {
		return GET_TEMPERATURE.call(target);
	}
}
