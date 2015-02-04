package openperipheral.integration.buildcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleBuildCraftTile extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|tiles";
	}

	@Override
	public void load() {
		IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterMachine());
	}
}
