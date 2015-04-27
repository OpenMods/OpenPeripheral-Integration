package openperipheral.integration.mystcraft.v2;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.meta.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleMystcraftV2 extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.MYSTCRAFT;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterWritingDesk());

		final IItemStackMetaBuilder builder = ApiAccess.getApi(IItemStackMetaBuilder.class);

		builder.register(new BookMetaProvider());
		builder.register(new PageMetaProvider());
		builder.register(new LinkingPanelMetaProvider());
	}
}
