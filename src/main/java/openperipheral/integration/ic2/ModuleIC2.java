package openperipheral.integration.ic2;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleIC2 extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.IC2;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterElecticMachine());
	}
}
