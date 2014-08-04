package openperipheral.integration.tmechworks;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;

public class ModuleTMechworks extends OPIntegrationModule {

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterDrawbridgeLogicBase());
	}

	@Override
	public String getModId() {
		return Mods.TINKERSMECHWORKS;
	}
}
