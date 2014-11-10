package openperipheral.integration.cofh.item;

import openperipheral.api.ApiAccess;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleCofhItem extends OPIntegrationModule {

	@Override
	public String getModId() {
		return "CoFHAPI|item";
	}

	@Override
	public void load() {
		final IItemStackMetadataBuilder itemMeta = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMeta.register(new AugumenedItemMetaProvider());
		itemMeta.register(new EmpoweredItemMetaProvider());
		itemMeta.register(new InventoryContainerMetaProvider());
	}
}
