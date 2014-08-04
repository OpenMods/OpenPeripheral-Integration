package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityZombieMetaProvider implements IEntityMetadataProvider<EntityZombie> {

	@Override
	public Class<? extends EntityZombie> getTargetClass() {
		return EntityZombie.class;
	}

	@Override
	public String getKey() {
		return "zombie";
	}

	@Override
	public Object getMeta(EntityZombie target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("isVillagerZombie", target.isVillager());
		map.put("convertingToVillager", target.isConverting());

		return map;
	}

}
