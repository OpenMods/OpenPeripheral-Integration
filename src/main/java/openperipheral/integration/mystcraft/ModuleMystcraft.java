package openperipheral.integration.mystcraft;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleMystcraft extends ModIntegrationModule {

	@Override
	public String getModId() {
		return Mods.MYSTCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterWritingDesk());

		final IItemStackMetaBuilder builder = ApiAccess.getApi(IItemStackMetaBuilder.class);
		builder.register(new BookMetaProvider());
		builder.register(new PageMetaProvider());
	}
}
