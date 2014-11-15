package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.CartTools;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class VanillaMinecraftMetaProvider extends EntityMetaProviderSimple<EntityMinecart> {

	@Override
	public String getKey() {
		return "railcraft_cart";
	}

	@Override
	public Object getMeta(EntityMinecart target, Vec3 relativePos) {
		Map<String, Object> result = Maps.newHashMap();
		boolean hasOwner = CartTools.doesCartHaveOwner(target);
		result.put("hasOwner", hasOwner);
		if (hasOwner) result.put("owner", CartTools.getCartOwner(target));
		return result;
	}
}
