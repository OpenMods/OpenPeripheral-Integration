package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class BossMetaProvider extends EntityMetaProviderSimple<IBossDisplayData> {

	@Override
	public String getKey() {
		return "boss";
	}

	@Override
	public Object getMeta(IBossDisplayData target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("name", target.func_145748_c_().getUnformattedText());

		return map;
	}

}
