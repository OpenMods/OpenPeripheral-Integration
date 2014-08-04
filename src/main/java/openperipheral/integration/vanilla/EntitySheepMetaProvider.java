package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntitySheepMetaProvider implements IEntityMetadataProvider<EntitySheep> {

	@Override
	public Class<? extends EntitySheep> getTargetClass() {
		return EntitySheep.class;
	}

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
