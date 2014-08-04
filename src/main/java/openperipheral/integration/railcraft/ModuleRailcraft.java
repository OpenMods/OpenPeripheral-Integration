package openperipheral.integration.railcraft;

import openmods.Mods;
import openperipheral.api.*;
import openperipheral.integration.OPIntegrationModule;

public class ModuleRailcraft extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.RAILCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterTileSteamTurbine());
		adapterRegistry.register(new AdapterTileBoilerFirebox());

		final IEntityMetadataBuilder entityMeta = ApiAccess.getApi(IEntityMetadataBuilder.class);
		entityMeta.register(new EnergyCartMetaProvider());
		entityMeta.register(new ExplosiveCartMetaProvider());
		entityMeta.register(new LinkableCartMetaProvider());
		entityMeta.register(new LiquidCartMetaProvider());
		entityMeta.register(new PaintedCartMetaProvider());
		entityMeta.register(new RefuealableCartMetaProvider());
		entityMeta.register(new RoutableCartMetaProvider());

		final IItemStackMetadataBuilder itemMeta = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMeta.register(new TicketMetaProvider());
	}
}
