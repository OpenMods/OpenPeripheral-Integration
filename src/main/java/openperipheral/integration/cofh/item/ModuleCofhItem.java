package openperipheral.integration.cofh.item;

import openperipheral.api.ApiAccess;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleCofhItem extends OPIntegrationModule {

	@Override
	public String getModId() {
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
