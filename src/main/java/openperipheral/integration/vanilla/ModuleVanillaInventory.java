package openperipheral.integration.vanilla;

import openmods.integration.IIntegrationModule;
import openperipheral.integration.OpcAccess;

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
		OpcAccess.adapterRegistry.register(new AdapterInventory());
	}
}
