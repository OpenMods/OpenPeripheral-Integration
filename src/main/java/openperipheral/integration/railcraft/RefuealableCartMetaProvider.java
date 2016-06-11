package openperipheral.integration.railcraft;

import com.google.common.collect.Maps;
import java.util.Map;
import mods.railcraft.api.carts.IRefuelableCart;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class RefuealableCartMetaProvider extends EntityMetaProviderSimple<IRefuelableCart> {

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
