package openperipheral.integration.thaumcraft;

import openperipheral.api.*;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleThaumcraftApi extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "Thaumcraft|API";
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterAspectContainer());
		adapterRegistry.register(new AdapterNode());
		adapterRegistry.register(new AdapterEssentiaTransport());

		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new EssentiaContainerMetaProvider());
		itemMeta.register(new WandFocusMetaProvider());
		itemMeta.register(new RunicItemMetaProvider());
		
		final ITypeConvertersRegistry converters = ApiAccess.getApi(ITypeConvertersRegistry.class);
		converters.register(new ConverterAspectList());
	}
}
