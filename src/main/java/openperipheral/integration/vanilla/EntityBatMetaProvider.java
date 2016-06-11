package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EntityBatMetaProvider extends EntityMetaProviderSimple<EntityBat> {

	@Override
	public String getKey() {
		return "bat";
	}

	@Override
	public Object getMeta(EntityBat target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("isHanging", target.getIsBatHanging());

		return map;
	}

}
