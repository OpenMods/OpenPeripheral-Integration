package openperipheral.integration.ic2;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleIC2Api extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "IC2API";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterReactor());
		adapterRegistry.register(new AdapterReactorChamber());
		adapterRegistry.register(new AdapterEnergyConductor());
		adapterRegistry.register(new AdapterEnergySink());
		adapterRegistry.register(new AdapterEnergySource());
		adapterRegistry.register(new AdapterEnergyStorage());
		adapterRegistry.register(new AdapterCrop());
		adapterRegistry.register(new AdapterKineticSource());
		adapterRegistry.register(new AdapterHeatSource());

		final IItemStackMetaBuilder itemMetaProvider = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMetaProvider.register(new ElectricItemMetaProvider());
	}
}
