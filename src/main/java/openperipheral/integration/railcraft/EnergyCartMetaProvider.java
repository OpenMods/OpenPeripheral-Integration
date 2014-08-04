package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.IExplosiveCart;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EnergyCartMetaProvider implements IEntityMetadataProvider<IExplosiveCart> {

	@Override
	public Class<? extends IExplosiveCart> getTargetClass() {
		return IExplosiveCart.class;
	}

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
