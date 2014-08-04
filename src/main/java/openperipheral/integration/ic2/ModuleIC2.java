package openperipheral.integration.ic2;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleIC2 extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.IC2;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterReactor());
		adapterRegistry.register(new AdapterReactorChamber());
		adapterRegistry.register(new AdapterMassFab());
		adapterRegistry.register(new AdapterEnergyConductor());
		adapterRegistry.register(new AdapterEnergySink());
		adapterRegistry.register(new AdapterEnergySource());
		adapterRegistry.register(new AdapterEnergyStorage());

		final IItemStackMetadataBuilder itemMetaProvider = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMetaProvider.register(new ElectricItemMetaProvider());
	}
}
