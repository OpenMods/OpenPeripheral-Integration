package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import openmods.utils.InventoryUtils;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

public class EntityPlayerMetaProvider extends EntityMetaProviderSimple<EntityPlayer> {

	@Override
	public String getKey() {
		return "player";
	}

	@Override
	public Object getMeta(EntityPlayer target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("inventory", InventoryUtils.getAllItems(target.inventory));
		map.put("isAirBorne", target.isAirBorne);
		map.put("isBlocking", target.isBlocking());
		map.put("profile", target.getGameProfile());
		map.put("foodLevel", target.getFoodStats().getFoodLevel());
		map.put("isCreativeMode", target.capabilities.isCreativeMode);

		return map;
	}

}
