package openperipheral.integration.cofh.energy;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.OPIntegrationModule;

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

		final IItemStackMetaBuilder itemMeta = ApiAccess.getApi(IItemStackMetaBuilder.class);
		itemMeta.register(new EnergyMetaProvider());
	}
}
