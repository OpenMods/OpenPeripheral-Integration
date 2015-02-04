package openperipheral.integration.cofh.item;

import openperipheral.api.ApiAccess;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ApiIntegrationModule;

public class ModuleCofhItem extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|item";
	}

	@Override
	public void load() {
		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new AugumenedItemMetaProvider());
		itemMeta.register(new EmpoweredItemMetaProvider());
		itemMeta.register(new InventoryContainerMetaProvider());
	}
}
