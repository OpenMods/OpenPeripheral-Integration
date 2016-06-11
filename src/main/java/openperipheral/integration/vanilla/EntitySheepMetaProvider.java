package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EntitySheepMetaProvider extends EntityMetaProviderSimple<EntitySheep> {

	@Override
	public String getKey() {
		return "sheep";
	}

	@Override
	public Object getMeta(EntitySheep target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("sheepColor", target.getFleeceColor());
		map.put("isSheared", target.getSheared());

		return map;
	}

}
