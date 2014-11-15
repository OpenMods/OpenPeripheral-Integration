package openperipheral.integration.forestry;

import openperipheral.api.*;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleForestry extends ApiIntegrationModule {
	@Override
	public String getApiId() {
		return "ForestryAPI|core";
	}

	@Override
	public void load() {
		final IAdapterRegistry peripheralRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		peripheralRegistry.register(new AdapterBeeHousing());

		final ITypeConvertersRegistry typeRegistry = ApiAccess.getApi(ITypeConvertersRegistry.class);
		typeRegistry.register(new ConverterIIndividual());

		ApiAccess.getApi(IItemStackMetaBuilder.class).register(new IndividualMetaProvider());
	}
}
