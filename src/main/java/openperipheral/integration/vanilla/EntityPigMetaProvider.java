package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityPigMetaProvider implements IEntityMetadataProvider<EntityPig> {

	@Override
	public Class<? extends EntityPig> getTargetClass() {
		return EntityPig.class;
	}

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
