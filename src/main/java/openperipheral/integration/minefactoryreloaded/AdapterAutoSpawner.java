package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterAutoSpawner implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner");

	private final Function0<Boolean> GET_SPAWN_EXACT = MethodAccess.create(boolean.class, CLASS, "getSpawnExact");
	private final Function1<Void, Boolean> SET_SPAWN_EXACT = MethodAccess.create(void.class, CLASS, boolean.class, "setSpawnExact");

	@Override
	public String getSourceId() {
		return "mfr_spawner";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@ScriptCallable(description = "Get value of spawn exact copy toggle", returnTypes = ReturnType.BOOLEAN)
	public boolean getSpawnExact(Object tileEntityAutoSpawner) {
		return GET_SPAWN_EXACT.call(tileEntityAutoSpawner);
	}

	@ScriptCallable(description = "Set the value of spawn exact copy")
	public void setSpawnExact(Object tileEntityAutoSpawner, @Arg(name = "spawnExact") boolean spawnExact) {
		SET_SPAWN_EXACT.call(tileEntityAutoSpawner, spawnExact);
	}

}
