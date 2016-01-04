package openperipheral.integration.buildcraft;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleBuildCraftTile extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|tiles";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterMachine());
	}
}
