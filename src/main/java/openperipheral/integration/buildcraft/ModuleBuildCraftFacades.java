package openperipheral.integration.buildcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleBuildCraftFacades extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "BuildCraftAPI|facades";
	}

	@Override
	public void load() {
		IItemStackMetaBuilder metaBuilder = ApiAccess.getApi(IItemStackMetaBuilder.class);
		metaBuilder.register(new FacadeMetaProvider());
	}
}
