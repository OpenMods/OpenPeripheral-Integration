package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.ILiquidTransfer;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class LiquidCartMetaProvider extends EntityMetaProviderSimple<ILiquidTransfer> {

	@Override
	public String getKey() {
		return "liquid_cart";
	}

	@Override
	public Object getMeta(ILiquidTransfer target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("isFilling", target.isFilling());
		return map;
	}

}
