package openperipheral.integration.thaumcraft;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleThaumcraft extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THAUMCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterJar());
		adapterRegistry.register(new AdapterBrainJar());
		adapterRegistry.register(new AdapterArcaneBore());
		adapterRegistry.register(new AdapterArcaneEar());
		adapterRegistry.register(new AdapterDeconstructor());
	}
}
