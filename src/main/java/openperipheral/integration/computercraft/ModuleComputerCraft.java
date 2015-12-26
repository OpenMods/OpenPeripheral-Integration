package openperipheral.integration.computercraft;

import openmods.Mods;
import openperipheral.integration.ModIntegrationModule;
import openperipheral.integration.OpcAccess;

public class ModuleComputerCraft extends ModIntegrationModule {
	@Override
	public String getModId() {
		return Mods.COMPUTERCRAFT;
	}

	@Override
	public void load() {
		OpcAccess.itemStackMetaBuilder.register(new ComputerMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new TurtleMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new MediaMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new PrintoutMetaProvider());
	}
}
