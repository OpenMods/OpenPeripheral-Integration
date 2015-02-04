package openperipheral.integration.railcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleRailcraftFuel extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "RailcraftAPI|fuel";
	}

	@Override
	public void load() {
		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new BoilerFuelMetaProvider());
	}
}
