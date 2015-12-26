package openperipheral.integration.ic2;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleIC2 extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.IC2;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterElecticMachine());
	}
}
