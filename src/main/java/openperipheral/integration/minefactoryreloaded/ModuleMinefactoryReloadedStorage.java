package openperipheral.integration.minefactoryreloaded;

import openmods.Mods;
import openmods.integration.Conditions;
import openperipheral.integration.CustomIntegrationModule;
import openperipheral.integration.OpcAccess;

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
		OpcAccess.adapterRegistry.register(new AdapterDeepStorage());
	}
}
