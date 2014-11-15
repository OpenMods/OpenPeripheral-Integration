package openperipheral.integration.buildcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleBuildCraftPower extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|power";
	}

	@Override
	public void load() {
		IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterPowerReceptor());
	}
}
