package openperipheral.integration.enderstorage;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleEnderStorage extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.ENDERSTORAGE;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterFrequencyOwner());
	}
}
