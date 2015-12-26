package openperipheral.integration.cofh.inventory;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleCofhInventory extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|inventory";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterInventoryHandler());
	}
}
