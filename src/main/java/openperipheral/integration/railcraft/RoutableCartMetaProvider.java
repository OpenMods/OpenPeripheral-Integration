package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class RoutableCartMetaProvider extends EntityMetaProviderSimple<IRoutableCart> {

	@Override
	public String getKey() {
		return "routable_cart";
	}

	@Override
	public Object getMeta(IRoutableCart target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("destination", target.getDestination());
		return map;
	}

}
