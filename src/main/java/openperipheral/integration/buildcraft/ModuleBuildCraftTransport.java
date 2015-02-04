package openperipheral.integration.buildcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleBuildCraftTransport extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|transport";
	}

	@Override
	public void load() {
		IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterPipe());
	}
}
