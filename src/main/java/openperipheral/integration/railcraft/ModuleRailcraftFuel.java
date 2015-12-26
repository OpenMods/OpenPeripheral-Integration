package openperipheral.integration.railcraft;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleRailcraftFuel extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "RailcraftAPI|fuel";
	}

	@Override
	public void load() {
		OpcAccess.itemStackMetaBuilder.register(new BoilerFuelMetaProvider());
	}
}
