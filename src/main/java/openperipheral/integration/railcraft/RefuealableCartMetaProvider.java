package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.IRefuelableCart;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class RefuealableCartMetaProvider implements IEntityMetadataProvider<IRefuelableCart> {

	@Override
	public Class<? extends IRefuelableCart> getTargetClass() {
		return IRefuelableCart.class;
	}

	@Override
	public String getKey() {
		return "refuelable_cart";
	}

	@Override
	public Object getMeta(IRefuelableCart target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("needsRefuel", target.needsRefuel());
		return map;
	}

}
