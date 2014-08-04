package openperipheral.integration.mystcraft;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleMystcraft extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.MYSTCRAFT;
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterWritingDesk());

		final IItemStackMetadataBuilder builder = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		builder.register(new BookMetaProvider());
		builder.register(new PageMetaProvider());
	}
}
