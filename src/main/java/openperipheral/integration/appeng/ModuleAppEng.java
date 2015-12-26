package openperipheral.integration.appeng;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleAppEng extends ModIntegrationModule {
	public static final String CC_EVENT_STATE_CHANGED = "crafting_state";

	@Override
	public String getModId() {
		return Mods.APPLIEDENERGISTICS2;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterStorage());
		OpcAccess.adapterRegistry.register(new AdapterInterface());
		OpcAccess.adapterRegistry.register(new AdapterNetwork());

		OpcAccess.converterManager.register(new ConverterAEItemStack());

		OpcAccess.itemStackMetaBuilder.register(new StorageCellMetaProvider());
	}

}
