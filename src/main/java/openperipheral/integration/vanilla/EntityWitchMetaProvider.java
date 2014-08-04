package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityWitchMetaProvider implements IEntityMetadataProvider<EntityWitch> {

	@Override
	public Class<? extends EntityWitch> getTargetClass() {
		return EntityWitch.class;
	}

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
