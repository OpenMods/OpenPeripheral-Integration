package openperipheral.integration.appeng;

import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.converter.IConverterManager;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleAppEng extends ModIntegrationModule {
	public static final String CC_EVENT_STATE_CHANGED = "crafting_state";

	@Override
	public String getModId() {
		return "appliedenergistics2";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterStorage());
		adapterRegistry.register(new AdapterInterface());
		adapterRegistry.register(new AdapterNetwork());

		final IConverterManager convertersRegistry = ApiAccess.getApi(IConverterManager.class);
		convertersRegistry.register(new ConverterAEItemStack());

		final IItemStackMetaBuilder stackBuilders = ApiAccess.getApi(IItemStackMetaBuilder.class);
		stackBuilders.register(new StorageCellMetaProvider());
	}

}
