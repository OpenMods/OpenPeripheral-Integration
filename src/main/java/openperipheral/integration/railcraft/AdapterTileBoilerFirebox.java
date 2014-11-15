package openperipheral.integration.railcraft;

import openmods.utils.ReflectionHelper;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterTileBoilerFirebox implements IPeripheralAdapter {
	private static final Class<?> CLAZZ = ReflectionHelper.getClass("mods.railcraft.common.blocks.machine.beta.TileBoilerFirebox");

	@Override
	public Class<?> getTargetClass() {
		return CLAZZ;
	}

	@Override
	public String getSourceId() {
		return "railcraft_firebox";
	}

	@LuaCallable(description = "Get whether the boiler is active or not", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isBurning(Object target) {
		return ReflectionHelper.call(target, "isBurning");
	}

	@LuaCallable(description = "Get the temperature of the boiler", returnTypes = LuaReturnType.NUMBER)
	public float getTemperature(Object target) {
		return ReflectionHelper.call(target, "getTemperature");
	}
}
