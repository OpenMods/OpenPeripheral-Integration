package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

public class EntityCreeperMetaProvider extends EntityMetaProviderSimple<EntityCreeper> {

	@Override
	public String getKey() {
		return "creeper";
	}

	@Override
	public Object getMeta(EntityCreeper target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("isCharged", target.getPowered());

		return map;
	}

}
