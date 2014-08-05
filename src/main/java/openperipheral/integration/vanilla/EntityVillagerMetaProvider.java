package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityVillagerMetaProvider implements IEntityMetadataProvider<EntityVillager> {

	@Override
	public Class<? extends EntityVillager> getTargetClass() {
		return EntityVillager.class;
	}

	@Override
	public String getKey() {
		return "villager";
	}

	@Override
	public Object getMeta(EntityVillager target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("profession", target.getProfession());
		map.put("isMating", target.isMating());
		map.put("isPlaying", target.isPlaying());

		boolean isTrading = target.isTrading();
		map.put("isTrading", isTrading);
		if (isTrading) map.put("customer", target.getCustomer().getGameProfile());

		return map;
	}

}
