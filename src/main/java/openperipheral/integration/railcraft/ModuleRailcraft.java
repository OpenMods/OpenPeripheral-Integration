package openperipheral.integration.railcraft;

import openmods.Log;
import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleRailcraft extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.RAILCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterTileSteamTurbine());
		adapterRegistry.register(new AdapterTileBoilerFirebox());

		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new TicketMetaProvider());

		Log.info("%s", TicketItemHolder.ticket);
	}
}
