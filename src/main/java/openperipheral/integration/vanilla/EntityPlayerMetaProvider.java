package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import openmods.utils.InventoryUtils;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityPlayerMetaProvider implements IEntityMetadataProvider<EntityPlayer> {

	@Override
	public Class<? extends EntityPlayer> getTargetClass() {
		return EntityPlayer.class;
	}

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
		map.put("username", target.username);
		map.put("foodLevel", target.getFoodStats().getFoodLevel());
		map.put("isCreativeMode", target.capabilities.isCreativeMode);

		return map;
	}

}
