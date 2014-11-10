package openperipheral.integration.cofh.energy;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;
import openperipheral.integration.cofh.tileentity.AdapterEnergyInfo;

public class ModuleCofhEnergy extends OPIntegrationModule {

	@Override
	public String getModId() {
		return "CoFHAPI|energy";
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterEnergyReceiver());
		adapterRegistry.register(new AdapterEnergyProvider());

		final IItemStackMetadataBuilder itemMeta = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMeta.register(new EnergyMetaProvider());
	}
}
