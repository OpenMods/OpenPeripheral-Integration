package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.IPaintedCart;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class PaintedCartMetaProvider implements IEntityMetadataProvider<IPaintedCart> {

	@Override
	public Class<? extends IPaintedCart> getTargetClass() {
		return IPaintedCart.class;
	}

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
