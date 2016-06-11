package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EntityWitchMetaProvider extends EntityMetaProviderSimple<EntityWitch> {

	@Override
	public String getKey() {
		return "witch";
	}

	@Override
	public Object getMeta(EntityWitch target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("isAggressive", target.getAggressive());

		return map;
	}

}
