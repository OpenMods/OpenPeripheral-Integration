package openperipheral.integration.thermalexpansion;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleThermalExpansion extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THERMALEXPANSION;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterTileLamp());
	}
}
