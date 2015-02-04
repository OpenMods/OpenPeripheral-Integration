package openperipheral.integration.cofh.inventory;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleCofhInventory extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|inventory";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterInventoryHandler());
	}
}
