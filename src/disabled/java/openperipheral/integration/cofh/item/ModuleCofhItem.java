package openperipheral.integration.cofh.item;

import openperipheral.integration.ApiIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleCofhItem extends ApiIntegrationModule {

	@Override
	public String getApiId() {
		return "CoFHAPI|item";
	}

	@Override
	public void load() {
		OpcAccess.itemStackMetaBuilder.register(new AugumenedItemMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new EmpoweredItemMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new InventoryContainerMetaProvider());
	}
}
