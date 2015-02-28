package openperipheral.integration.minefactoryreloaded;

import openmods.Mods;
import openmods.integration.Conditions;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.CustomIntegrationModule;

public class ModuleMinefactoryReloadedStorage extends CustomIntegrationModule {

	public ModuleMinefactoryReloadedStorage() {
		super(Conditions.classExists("powercrystals.minefactoryreloaded.api.IDeepStorageUnit"));
	}

	@Override
	protected String getId() {
		return Mods.MFR + " deep storage";
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterDeepStorage());
	}
}
