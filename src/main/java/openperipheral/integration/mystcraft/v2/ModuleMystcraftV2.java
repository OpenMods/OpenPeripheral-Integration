package openperipheral.integration.mystcraft.v2;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleMystcraftV2 extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.MYSTCRAFT;
	}

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterWritingDesk());

		OpcAccess.itemStackMetaBuilder.register(new BookMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new PageMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new LinkingPanelMetaProvider());
	}
}
