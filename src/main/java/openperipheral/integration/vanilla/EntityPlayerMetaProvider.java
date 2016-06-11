package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EntityPlayerMetaProvider extends EntityMetaProviderSimple<EntityPlayer> {

	@Override
	public String getKey() {
		return "player";
	}

	@Override
	public Object getMeta(EntityPlayer target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("inventory", InventoryMetaProvider.wrapToProxyTable(target.inventory));
		map.put("isAirBorne", target.isAirBorne);
		map.put("isBlocking", target.isBlocking());
		map.put("profile", target.getGameProfile());
		map.put("foodLevel", target.getFoodStats().getFoodLevel());
		map.put("isCreativeMode", target.capabilities.isCreativeMode);

		Map<String, Object> experience = Maps.newHashMap();
		experience.put("level", target.experienceLevel);
		experience.put("levelProgress", target.experience);
		experience.put("nextLevelXp", target.xpBarCap());
		map.put("experience", experience);

		return map;
	}

}
