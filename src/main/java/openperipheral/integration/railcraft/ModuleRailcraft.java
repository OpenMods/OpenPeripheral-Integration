package openperipheral.integration.railcraft;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleRailcraft extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.RAILCRAFT;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterTileSteamTurbine());
		adapterRegistry.register(new AdapterTileBoilerFirebox());

		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new TicketMetaProvider());
	}
}
