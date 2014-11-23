package openperipheral.integration;

import openmods.integration.IIntegrationModule;
import cpw.mods.fml.common.ModAPIManager;

public abstract class ApiIntegrationModule implements IIntegrationModule {

	public abstract String getApiId();

	@Override
	public String name() {
		return getApiId() + " (API) CC integration module";
	}

	@Override
	public boolean canLoad() {
		final String apiId = getApiId();
		return ModAPIManager.INSTANCE.hasAPI(apiId);
	}
}
