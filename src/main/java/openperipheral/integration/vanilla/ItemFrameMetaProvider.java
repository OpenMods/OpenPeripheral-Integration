package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class ItemFrameMetaProvider extends EntityMetaProviderSimple<EntityItemFrame> {

	@Override
	public String getKey() {
		return "item_frame";
	}

	@Override
	public Object getMeta(EntityItemFrame target, Vec3 relativePos) {
		Map<String, Object> result = Maps.newHashMap();

		result.put("item", target.getDisplayedItem());
		result.put("rotation", target.getRotation());

		return result;
	}

}
