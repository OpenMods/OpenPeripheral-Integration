package openperipheral.integration.enderstorage;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;

public class ModuleEnderStorage extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.ENDERSTORAGE;
	}

	@Override
	public void load() {
		final IAdapterRegistry api = ApiAccess.getApi(IAdapterRegistry.class);

		api.register(new AdapterFrequencyOwner());
	}
}
