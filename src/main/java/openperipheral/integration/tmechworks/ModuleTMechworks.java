package openperipheral.integration.tmechworks;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleTMechworks extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.TINKERSMECHWORKS;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterDrawbridgeLogicBase());
	}
}
