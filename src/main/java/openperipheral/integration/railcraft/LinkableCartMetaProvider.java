package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.CartTools;
import mods.railcraft.api.carts.ILinkableCart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class LinkableCartMetaProvider implements IEntityMetadataProvider<ILinkableCart> {

	@Override
	public Class<? extends ILinkableCart> getTargetClass() {
		return ILinkableCart.class;
	}

	@Override
	public String getKey() {
		return "linkable_cart";
	}

	@Override
	public Object getMeta(ILinkableCart target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		boolean linkable = target.isLinkable();
		map.put("linkable", linkable);

		if (target instanceof EntityMinecart) {
			EntityMinecart cart = (EntityMinecart)target;

			if (linkable) map.put("cartsInTrain", CartTools.linkageManager.countCartsInTrain(cart));

			boolean hasOwner = CartTools.doesCartHaveOwner(cart);
			map.put("hasOwner", hasOwner);
			if (hasOwner) map.put("owner", CartTools.getCartOwner(cart));
		}
		return map;
	}

}
