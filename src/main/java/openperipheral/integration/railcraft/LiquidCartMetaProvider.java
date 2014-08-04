package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.carts.ILiquidTransfer;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class LiquidCartMetaProvider implements IEntityMetadataProvider<ILiquidTransfer> {

	@Override
	public Class<? extends ILiquidTransfer> getTargetClass() {
		return ILiquidTransfer.class;
	}

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
