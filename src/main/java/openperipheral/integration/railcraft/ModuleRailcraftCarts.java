package openperipheral.integration.railcraft;

import openperipheral.api.ApiAccess;
import openperipheral.api.IEntityMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleRailcraftCarts extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "RailcraftAPI|carts";
	}

	@Override
	public void load() {
		final IEntityMetaBuilder entityMeta = ApiAccess.getApi(IEntityMetaBuilder.class);
		entityMeta.register(new ExplosiveCartMetaProvider());
		entityMeta.register(new EnergyCartMetaProvider());
		entityMeta.register(new LinkableCartMetaProvider());
		entityMeta.register(new LiquidCartMetaProvider());
		entityMeta.register(new PaintedCartMetaProvider());
		entityMeta.register(new RefuealableCartMetaProvider());
		entityMeta.register(new RoutableCartMetaProvider());
	}
}
