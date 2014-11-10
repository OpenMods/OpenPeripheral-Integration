package openperipheral.integration.cofh.inventory;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;

public class ModuleCofhInventory extends OPIntegrationModule {

	@Override
	public String getModId() {
		return "CoFHAPI|inventory";
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterInventoryHandler());
	}
}
