package openperipheral.integration.computercraft;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IItemStackMetaBuilder;
import openperipheral.integration.ModIntegrationModule;

public class ModuleComputerCraft extends ModIntegrationModule {
	@Override
	public String getModId() {
		return Mods.COMPUTERCRAFT;
	}

	@Override
	public void load() {
		IItemStackMetaBuilder api = ApiAccess.getApi(IItemStackMetaBuilder.class);

		api.register(new ComputerMetaProvider());
		api.register(new TurtleMetaProvider());
		api.register(new MediaMetaProvider());
		api.register(new PrintoutMetaProvider());
	}
}
