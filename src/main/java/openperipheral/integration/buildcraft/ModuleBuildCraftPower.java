package openperipheral.integration.buildcraft;

import static openmods.conditions.Conditions.all;
import static openmods.integration.Conditions.classExists;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.integration.CustomIntegrationModule;

public class ModuleBuildCraftPower extends CustomIntegrationModule {

	public ModuleBuildCraftPower() {
		super(all(classExists("buildcraft.api.power.IPowerReceptor"),
				classExists("buildcraft.api.power.PowerHandler")));
	}

	@Override
	public void load() {
		IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterPowerReceptor());
	}

	@Override
	protected String getId() {
		return "BuildCraft-MJ";
	}
}
