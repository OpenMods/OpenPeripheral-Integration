package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class BossMetaProvider implements IEntityMetadataProvider<IBossDisplayData> {

	@Override
	public Class<? extends IBossDisplayData> getTargetClass() {
		return IBossDisplayData.class;
	}

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
