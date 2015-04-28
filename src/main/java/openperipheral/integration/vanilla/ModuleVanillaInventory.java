package openperipheral.integration.vanilla;

import openmods.integration.IIntegrationModule;
import openperipheral.api.ApiAccess;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;

public class ModuleVanillaInventory implements IIntegrationModule {
	public static String DUMMY_VANILLA_MODID = "vanilla";

	@Override
	public String name() {
		return "Vanilla CC integration module (inventory)";
	}

	@Override
	public boolean canLoad() {
		return true;
	}

	@Override
	public void load() {
		final IPeripheralAdapterRegistry adapterRegistry = ApiAccess.getApi(IPeripheralAdapterRegistry.class);
		adapterRegistry.register(new AdapterInventory());
	}
}
