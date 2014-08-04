package openperipheral.integration.thaumcraft;

import java.util.List;
import java.util.Map;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ModuleThaumcraft extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THAUMCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterAspectContainer());
		adapterRegistry.register(new AdapterNode());
		adapterRegistry.register(new AdapterJar());
		adapterRegistry.register(new AdapterBrainJar());
		adapterRegistry.register(new AdapterArcaneBore());
		adapterRegistry.register(new AdapterArcaneEar());
		adapterRegistry.register(new AdapterDeconstructor());
		adapterRegistry.register(new AdapterEssentiaTransport());
	}

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
}
