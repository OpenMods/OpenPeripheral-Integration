package openperipheral.integration.buildcraft;

import static openmods.conditions.Conditions.all;
import static openmods.integration.Conditions.classExists;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.CustomIntegrationModule;

public class ModuleBuildCraftPower extends CustomIntegrationModule {

	public ModuleBuildCraftPower() {
		super(all(classExists("buildcraft.api.power.IPowerReceptor"),
				classExists("buildcraft.api.power.PowerHandler")));
	}

	@Override
	public void load() {
		IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterPowerReceptor());
	}

	@Override
	protected String getId() {
		return "BuildCraft-MJ";
	}
}
