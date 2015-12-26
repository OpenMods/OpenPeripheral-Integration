package openperipheral.integration.forestry;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleForestry extends ApiIntegrationModule {
	@Override
	public String getApiId() {
		return "ForestryAPI|core";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterBeeHousing());

		OpcAccess.converterManager.register(new ConverterIIndividual());

		OpcAccess.itemStackMetaBuilder.register(new IndividualMetaProvider());
	}
}
