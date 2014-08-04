package openperipheral.integration.thermalexpansion;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleThermalExpansion extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.THERMALEXPANSION;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterEnergyHandler());
		adapterRegistry.register(new AdapterEnderAttuned());
		adapterRegistry.register(new AdapterEnergyInfo());
		adapterRegistry.register(new AdapterTileLamp());
		adapterRegistry.register(new AdapterSecureTile());

		final IItemStackMetadataBuilder itemMeta = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMeta.register(new EnergyMetaProvider());
		itemMeta.register(new ContainerMetaProvider());
		itemMeta.register(new SecureItemMetaProvider());
	}
}
