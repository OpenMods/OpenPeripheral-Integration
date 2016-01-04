package openperipheral.integration.cofh.energy;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleCofhEnergy extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|energy";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterEnergyReceiver());
		OpcAccess.adapterRegistry.register(new AdapterEnergyProvider());

		OpcAccess.itemStackMetaBuilder.register(new EnergyMetaProvider());
	}
}
