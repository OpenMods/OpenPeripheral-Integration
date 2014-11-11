package openperipheral.integration.cofh.inventory;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleCofhInventory extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|inventory";
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterInventoryHandler());
	}
}
