package openperipheral.integration.cofh.transport;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleCofhTransport extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|transport";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterEnderEnergyAttuned());
		adapterRegistry.register(new AdapterEnderItemAttuned());
		adapterRegistry.register(new AdapterEnderFluidAttuned());
	}
}
