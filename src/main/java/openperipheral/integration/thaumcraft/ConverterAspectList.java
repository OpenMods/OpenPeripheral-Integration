package openperipheral.integration.thaumcraft;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import openperipheral.api.converter.IConverter;
import openperipheral.api.helpers.SimpleOutboundConverter;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ConverterAspectList extends SimpleOutboundConverter<AspectList> {

	public static void appendAspectEntry(List<Map<String, Object>> result, Aspect aspect, int quantity) {
		Map<String, Object> aspectDetails = Maps.newHashMap();
		aspectDetails.put("name", aspect.getName());
		aspectDetails.put("quantity", quantity);
		result.add(aspectDetails);
	}

	public static List<Map<String, Object>> aspectsToMap(AspectList aspectList) {
		List<Map<String, Object>> aspects = Lists.newArrayList();
		if (aspectList == null) return aspects;

		for (Aspect aspect : aspectList.getAspects()) {
			if (aspect == null) continue;
			appendAspectEntry(aspects, aspect, aspectList.getAmount(aspect));
		}
		return aspects;
	}

	@Override
	public Object convert(IConverter registry, AspectList obj) {
		return aspectsToMap(obj);
	}

}
