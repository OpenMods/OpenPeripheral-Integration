package openperipheral.integration.thermalexpansion;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleThermalExpansion extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THERMALEXPANSION;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterTileLamp());
	}
}
