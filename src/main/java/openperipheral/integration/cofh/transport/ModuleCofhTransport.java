package openperipheral.integration.cofh.transport;

import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;
import openperipheral.integration.cofh.energy.AdapterEnergyReceiver;
import openperipheral.integration.cofh.energy.EnergyMetaProvider;
import openperipheral.integration.cofh.tileentity.AdapterEnergyInfo;

public class ModuleCofhTransport extends OPIntegrationModule {

	@Override
	public String getModId() {
		return "CoFHAPI|transport";
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterEnergyReceiver());
		adapterRegistry.register(new AdapterEnderItemAttuned());
		adapterRegistry.register(new AdapterEnergyInfo());

		final IItemStackMetadataBuilder itemMeta = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMeta.register(new EnergyMetaProvider());
	}
}
