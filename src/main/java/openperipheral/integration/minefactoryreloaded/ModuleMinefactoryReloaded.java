package openperipheral.integration.minefactoryreloaded;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleMinefactoryReloaded extends ModIntegrationModule {

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

		final IItemStackMetaBuilder itemMetaBuilder = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMetaBuilder.register(new SafariNetMetaProvider());
	}
}
