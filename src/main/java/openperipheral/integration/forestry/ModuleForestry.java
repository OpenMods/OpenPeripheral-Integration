package openperipheral.integration.forestry;

import openmods.Mods;
import openperipheral.api.*;
import openperipheral.integration.OPIntegrationModule;

public class ModuleForestry extends OPIntegrationModule {
	@Override
	public String getModId() {
		return Mods.FORESTRY;
	}

	@Override
	public void load() {
		final IAdapterRegistry peripheralRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		peripheralRegistry.register(new AdapterBeeHousing());

		final ITypeConvertersRegistry typeRegistry = ApiAccess.getApi(ITypeConvertersRegistry.class);
		typeRegistry.register(new ConverterIIndividual());

		ApiAccess.getApi(IItemStackMetadataBuilder.class).register(new IndividualMetaProvider());
	}
}
