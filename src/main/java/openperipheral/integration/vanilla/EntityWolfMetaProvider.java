package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.Vec3;
import openmods.colors.ColorMeta;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

public class EntityWolfMetaProvider extends EntityMetaProviderSimple<EntityWolf> {

	@Override
	public String getKey() {
		return "wolf";
	}

	@Override
	public Object getMeta(EntityWolf target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("isAngry", target.isAngry());
		map.put("collarColor", ColorMeta.fromVanillaEnum(target.getCollarColor()).bitmask);

		return map;
	}

}
