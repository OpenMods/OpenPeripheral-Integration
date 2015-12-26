package openperipheral.integration.railcraft;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleRailcraftCarts extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "RailcraftAPI|carts";
	}

	@Override
	public void load() {
		OpcAccess.entityMetaBuilder.register(new ExplosiveCartMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EnergyCartMetaProvider());
		OpcAccess.entityMetaBuilder.register(new LinkableCartMetaProvider());
		OpcAccess.entityMetaBuilder.register(new LiquidCartMetaProvider());
		OpcAccess.entityMetaBuilder.register(new PaintedCartMetaProvider());
		OpcAccess.entityMetaBuilder.register(new RefuealableCartMetaProvider());
		OpcAccess.entityMetaBuilder.register(new RoutableCartMetaProvider());
	}
}
