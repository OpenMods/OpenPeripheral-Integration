package openperipheral.integration.enderstorage;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleEnderStorage extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.ENDERSTORAGE;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry api = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		api.register(new AdapterFrequencyOwner());
	}
}
