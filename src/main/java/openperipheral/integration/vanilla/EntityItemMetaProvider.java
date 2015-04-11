package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

public class EntityItemMetaProvider extends EntityMetaProviderSimple<EntityItem> {

	@Override
	public String getKey() {
		return "item";
	}

	@Override
	public Object getMeta(EntityItem target, Vec3 relativePos) {
		Map<String, Object> result = Maps.newHashMap();

		result.put("stack", target.getEntityItem());
		result.put("age", target.age);
		result.put("lifespan", target.lifespan);

		return result;
	}

}
