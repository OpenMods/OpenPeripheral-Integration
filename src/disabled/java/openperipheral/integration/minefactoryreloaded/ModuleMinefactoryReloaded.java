package openperipheral.integration.minefactoryreloaded;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleMinefactoryReloaded extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.MFR;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterAutoAnvil());
		OpcAccess.adapterRegistry.register(new AdapterAutoDisenchanter());
		OpcAccess.adapterRegistry.register(new AdapterAutoEnchanter());
		OpcAccess.adapterRegistry.register(new AdapterAutoJukebox());
		OpcAccess.adapterRegistry.register(new AdapterAutoSpawner());
		OpcAccess.adapterRegistry.register(new AdapterChronotyper());
		OpcAccess.adapterRegistry.register(new AdapterChunkLoader());
		OpcAccess.adapterRegistry.register(new AdapterEjector());
		OpcAccess.adapterRegistry.register(new AdapterHarvester());

		OpcAccess.itemStackMetaBuilder.register(new SafariNetMetaProvider());
	}
}
