package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EntityPigMetaProvider extends EntityMetaProviderSimple<EntityPig> {

	@Override
	public String getKey() {
		return "pig";
	}

	@Override
	public Object getMeta(EntityPig target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("isSaddled", target.getSaddled());

		return map;
	}

}
