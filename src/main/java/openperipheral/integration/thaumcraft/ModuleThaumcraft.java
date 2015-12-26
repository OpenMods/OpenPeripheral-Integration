package openperipheral.integration.thaumcraft;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleThaumcraft extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THAUMCRAFT;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterJar());
		OpcAccess.adapterRegistry.register(new AdapterBrainJar());
		OpcAccess.adapterRegistry.register(new AdapterArcaneBore());
		OpcAccess.adapterRegistry.register(new AdapterArcaneEar());
		OpcAccess.adapterRegistry.register(new AdapterDeconstructor());
	}
}
