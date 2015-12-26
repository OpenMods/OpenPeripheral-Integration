package openperipheral.integration.thaumcraft;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleThaumcraftApi extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "Thaumcraft|API";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterAspectContainer());
		OpcAccess.adapterRegistry.register(new AdapterNode());
		OpcAccess.adapterRegistry.register(new AdapterEssentiaTransport());

		OpcAccess.itemStackMetaBuilder.register(new EssentiaContainerMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new WandFocusMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new RunicItemMetaProvider());

		OpcAccess.converterManager.register(new ConverterAspectList());
	}
}
