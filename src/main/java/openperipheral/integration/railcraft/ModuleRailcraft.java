package openperipheral.integration.railcraft;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleRailcraft extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.RAILCRAFT;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterTileSteamTurbine());
		OpcAccess.adapterRegistry.register(new AdapterTileBoilerFirebox());

		OpcAccess.itemStackMetaBuilder.register(new TicketMetaProvider());
	}
}
