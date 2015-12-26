package openperipheral.integration.cofh.tileentity;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleCofhTileEntity extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|tileentity";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterSecureTile());
		OpcAccess.adapterRegistry.register(new AdapterEnergyInfo());
		OpcAccess.adapterRegistry.register(new AdapterReconfigurableFacing());
		OpcAccess.adapterRegistry.register(new AdapterReconfigurableSides());
		OpcAccess.adapterRegistry.register(new AdapterRedstoneControl());
	}
}
