package openperipheral.integration.sgcraft;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;

public class ModuleSgCraft extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.SGCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterStargate());
	}
}
