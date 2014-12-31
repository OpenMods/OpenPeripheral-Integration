package openperipheral.integration.thermalexpansion;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleThermalExpansion extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THERMALEXPANSION;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterTileLamp());
	}
}
