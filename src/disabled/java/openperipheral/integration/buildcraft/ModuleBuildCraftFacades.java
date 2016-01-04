package openperipheral.integration.buildcraft;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleBuildCraftFacades extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|facades";
	}

	@Override
	public void load() {
		OpcAccess.itemStackMetaBuilder.register(new FacadeMetaProvider());
	}
}
