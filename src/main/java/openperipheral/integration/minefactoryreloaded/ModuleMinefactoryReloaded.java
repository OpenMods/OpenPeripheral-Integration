package openperipheral.integration.minefactoryreloaded;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleMinefactoryReloaded extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.MFR;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterAutoAnvil());
		adapterRegistry.register(new AdapterAutoDisenchanter());
		adapterRegistry.register(new AdapterAutoEnchanter());
		adapterRegistry.register(new AdapterAutoJukebox());
		adapterRegistry.register(new AdapterAutoSpawner());
		adapterRegistry.register(new AdapterChronotyper());
		adapterRegistry.register(new AdapterChunkLoader());
		adapterRegistry.register(new AdapterEjector());
		adapterRegistry.register(new AdapterHarvester());

		final IItemStackMetadataBuilder itemMetaBuilder = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMetaBuilder.register(new SafariNetMetaProvider());
	}
}
