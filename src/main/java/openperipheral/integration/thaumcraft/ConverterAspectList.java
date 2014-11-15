package openperipheral.integration.thaumcraft;

import java.util.List;
import java.util.Map;

import openperipheral.api.ITypeConverter;
import openperipheral.api.ITypeConvertersRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ConverterAspectList implements ITypeConverter {

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
	public Object fromLua(ITypeConvertersRegistry registry, Object obj, Class<?> expected) {
		return null;
	}

	@Override
	public Object toLua(ITypeConvertersRegistry registry, Object obj) {
		if (obj instanceof AspectList) return aspectsToMap((AspectList)obj);

		return null;
	}

}
