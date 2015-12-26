package openperipheral.integration.ic2;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleIC2Api extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "IC2API";
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterReactor());
		OpcAccess.adapterRegistry.register(new AdapterReactorChamber());
		OpcAccess.adapterRegistry.register(new AdapterEnergyConductor());
		OpcAccess.adapterRegistry.register(new AdapterEnergySink());
		OpcAccess.adapterRegistry.register(new AdapterEnergySource());
		OpcAccess.adapterRegistry.register(new AdapterEnergyStorage());
		OpcAccess.adapterRegistry.register(new AdapterCrop());
		OpcAccess.adapterRegistry.register(new AdapterKineticSource());
		OpcAccess.adapterRegistry.register(new AdapterHeatSource());

		OpcAccess.itemStackMetaBuilder.register(new ElectricItemMetaProvider());
	}
}
