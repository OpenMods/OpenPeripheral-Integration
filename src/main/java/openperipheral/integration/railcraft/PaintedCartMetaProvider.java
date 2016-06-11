package openperipheral.integration.railcraft;

import com.google.common.collect.Maps;
import java.util.Map;
import mods.railcraft.api.carts.IPaintedCart;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class PaintedCartMetaProvider extends EntityMetaProviderSimple<IPaintedCart> {

	@Override
	public String getKey() {
		return "painted_cart";
	}

	@Override
	public Object getMeta(IPaintedCart target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("primaryColor", target.getPrimaryColor());
		map.put("secondaryColor", target.getSecondaryColor());
		return map;
	}

}
