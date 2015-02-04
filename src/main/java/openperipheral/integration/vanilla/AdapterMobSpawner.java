package openperipheral.integration.vanilla;

import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterMobSpawner implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityMobSpawner.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_spawner";
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "The name of the mob that spawns from the spawner")
	public String getSpawningMobName(TileEntityMobSpawner spawner) {
		return getSpawnerLogic(spawner).getEntityNameToSpawn();
	}

	private static MobSpawnerBaseLogic getSpawnerLogic(TileEntityMobSpawner spawner) {
		return spawner.func_145881_a();
	}
}