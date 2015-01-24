package openperipheral.integration.appeng;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.ITypeConvertersRegistry;
import openperipheral.integration.ModIntegrationModule;

public class ModuleAppEng extends ModIntegrationModule {
	public static final String CC_EVENT_STATE_CHANGED = "crafting_state";

	@Override
	public String getModId() {
		// TODO: This should be openmods.Mods.APPLIEDENERGISTICS, but it
		// currently contains the wrong modid.
		return "appliedenergistics2";
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterInterface());
		adapterRegistry.register(new AdapterNetwork());

		final ITypeConvertersRegistry convertersRegistry = ApiAccess.getApi(ITypeConvertersRegistry.class);
		convertersRegistry.register(new ConverterAEItemStack());
	}

}
