package openperipheral.integration.appeng;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.api.ITypeConvertersRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleAppEng extends ModIntegrationModule {
	public static final String CC_EVENT_STATE_CHANGED = "crafting_state";

	@Override
	public String getModId() {
		return Mods.APPLIEDENERGISTICS2;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterChest());
		adapterRegistry.register(new AdapterDrive());
		adapterRegistry.register(new AdapterInterface());
		adapterRegistry.register(new AdapterNetwork());

		final ITypeConvertersRegistry convertersRegistry = ApiAccess.getApi(ITypeConvertersRegistry.class);
		convertersRegistry.register(new ConverterSearchNeedle());

		final IItemStackMetaBuilder metaBuilder = ApiAccess.getApi(IItemStackMetaBuilder.class);
		metaBuilder.register(new NBTHashMetaProvider());
		metaBuilder.register(new AEItemStackMetaProvider());
	}
}
