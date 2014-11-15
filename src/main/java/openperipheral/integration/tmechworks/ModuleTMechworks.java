package openperipheral.integration.tmechworks;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleTMechworks extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.TINKERSMECHWORKS;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterDrawbridgeLogicBase());
	}
}
