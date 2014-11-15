package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.IExplosiveCart;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class ExplosiveCartMetaProvider extends EntityMetaProviderSimple<IExplosiveCart> {

	@Override
	public String getKey() {
		return "explosive_cart";
	}

	@Override
	public Object getMeta(IExplosiveCart target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("primed", target.isPrimed());
		map.put("fuse", target.getFuse());
		return map;
	}

}
