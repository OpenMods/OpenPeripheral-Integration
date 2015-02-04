package openperipheral.integration.cofh.tileentity;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleCofhTileEntity extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|tileentity";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterSecureTile());
		adapterRegistry.register(new AdapterEnergyInfo());
		adapterRegistry.register(new AdapterReconfigurableFacing());
		adapterRegistry.register(new AdapterReconfigurableSides());
		adapterRegistry.register(new AdapterRedstoneControl());
	}
}
