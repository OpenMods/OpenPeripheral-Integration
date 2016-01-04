package openperipheral.integration.buildcraft;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleBuildCraftTransport extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|transport";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterPipe());
	}
}
