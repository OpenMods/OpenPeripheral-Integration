package openperipheral.integration.cofh.transport;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleCofhTransport extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|transport";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterEnderEnergyAttuned());
		OpcAccess.adapterRegistry.register(new AdapterEnderItemAttuned());
		OpcAccess.adapterRegistry.register(new AdapterEnderFluidAttuned());
	}
}
