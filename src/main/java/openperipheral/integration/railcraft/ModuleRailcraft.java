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
		OpcAccess.adapterRegistry.register(new AdapterHasCart());
		OpcAccess.adapterRegistry.register(new AdapterHasWork());
		OpcAccess.adapterRegistry.register(new AdapterNeedsFuel());
		OpcAccess.adapterRegistry.register(new AdapterNeedsMaintenance());
		OpcAccess.adapterRegistry.register(new AdapterTemperature());

		OpcAccess.itemStackMetaBuilder.register(new TicketMetaProvider());
	}
}
