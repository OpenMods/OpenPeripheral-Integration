package openperipheral.integration.thaumcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.converter.IConverterManager;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleThaumcraftApi extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "Thaumcraft|API";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterAspectContainer());
		adapterRegistry.register(new AdapterNode());
		adapterRegistry.register(new AdapterEssentiaTransport());

		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new EssentiaContainerMetaProvider());
		itemMeta.register(new WandFocusMetaProvider());
		itemMeta.register(new RunicItemMetaProvider());

		final IConverterManager converters = ApiAccess.getApi(IConverterManager.class);
		converters.register(new ConverterAspectList());
	}
}
