package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterAutoSpawner implements IPeripheralAdapter {
	private static final Class<?> AUTOSPAWNER_CLASS = ReflectionHelper.getClass(
			"powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner"
			);

	@Override
	public String getSourceId() {
		return "mfr_spawner";
	}

	@Override
	public Class<?> getTargetClass() {
		return AUTOSPAWNER_CLASS;
	}

	@LuaCallable(description = "Get value of spawn exact copy toggle", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getSpawnExact(Object tileEntityAutoSpawner) {
		return ReflectionHelper.call(tileEntityAutoSpawner, "getSpawnExact");
	}

	@LuaCallable(description = "Set the value of spawn exact copy")
	public void setSpawnExact(Object tileEntityAutoSpawner, @Arg(name = "spawnExact") boolean spawnExact) {
		ReflectionHelper.call(tileEntityAutoSpawner, "setSpawnExact", ReflectionHelper.primitive(spawnExact));
	}

}
