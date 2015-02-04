package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

public class EntityTameableMetaProvider extends EntityMetaProviderSimple<EntityTameable> {

	@Override
	public String getKey() {
		return "tameable";
	}

	@Override
	public Object getMeta(EntityTameable target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		boolean isTamed = target.isTamed();
		map.put("isTamed", isTamed);
		if (isTamed) {
			map.put("isSitting", target.isSitting());
			map.put("ownerUUID", target.func_152113_b());
		}

		return map;
	}

}
