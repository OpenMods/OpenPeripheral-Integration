package openperipheral.integration.projectred;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;

public class ModuleProjectRed extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.PROJECTRED_TRANSMISSION;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterBundledCablePart());
		adapterRegistry.register(new AdapterInsulatedRedwirePart());
	}
}
