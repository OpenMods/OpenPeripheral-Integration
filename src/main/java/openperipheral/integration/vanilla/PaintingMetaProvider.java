package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class PaintingMetaProvider extends EntityMetaProviderSimple<EntityPainting> {

	@Override
	public String getKey() {
		return "painting";
	}

	@Override
	public Object getMeta(EntityPainting target, Vec3 relativePos) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("title", target.art.title);
		result.put("width", target.art.sizeX / 16);
		result.put("height", target.art.sizeY / 16);
		return result;
	}

}
