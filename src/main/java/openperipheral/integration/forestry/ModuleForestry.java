package openperipheral.integration.forestry;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.converter.IConverterManager;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleForestry extends ApiIntegrationModule {
	@Override
	public String getApiId() {
		return "ForestryAPI|core";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry peripheralRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		peripheralRegistry.register(new AdapterBeeHousing());

		final IConverterManager typeRegistry = ApiAccess.getApi(IConverterManager.class);
		typeRegistry.register(new ConverterIIndividual());

		ApiAccess.getApi(IItemStackMetaBuilder.class).register(new IndividualMetaProvider());
	}
}
