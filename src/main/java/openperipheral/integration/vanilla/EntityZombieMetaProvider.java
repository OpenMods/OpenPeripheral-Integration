package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EntityZombieMetaProvider extends EntityMetaProviderSimple<EntityZombie> {

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
