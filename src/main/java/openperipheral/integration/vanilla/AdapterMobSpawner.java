package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

import com.google.common.collect.Maps;

public class AdapterMobSpawner implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityMobSpawner.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_spawner";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Information about spawner")
	public Map<String, Object> getSpawnerInfo(TileEntityMobSpawner spawner) {
		final NBTTagCompound tag = new NBTTagCompound();
		spawner.getSpawnerBaseLogic().writeToNBT(tag);

		final Map<String, Object> result = Maps.newHashMap();

		putString(result, tag, "EntityId");
		putShort(result, tag, "Delay");
		putShort(result, tag, "MinSpawnDelay");
		putShort(result, tag, "MaxSpawnDelay");
		putShort(result, tag, "SpawnCount");
		putShort(result, tag, "MaxNearbyEntities");
		putShort(result, tag, "RequiredPlayerRange");
		putShort(result, tag, "SpawnRange");

		return result;
	}

	private static void putString(Map<String, Object> result, NBTTagCompound tag, String key) {
		result.put(key, tag.getString(key));
	}

	private static void putShort(Map<String, Object> result, NBTTagCompound tag, String key) {
		result.put(key, tag.getShort(key));
	}
}