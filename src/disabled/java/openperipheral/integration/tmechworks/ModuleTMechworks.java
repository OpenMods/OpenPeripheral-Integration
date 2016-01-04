package openperipheral.integration.tmechworks;

import openmods.Mods;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleTMechworks extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.TINKERSMECHWORKS;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = OpcAccess.adapterRegistry;
		adapterRegistry.register(new AdapterDrawbridgeLogicBase());
	}
}
