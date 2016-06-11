package openperipheral.integration;

import cpw.mods.fml.common.Loader;
import openmods.integration.IIntegrationModule;

public abstract class ModIntegrationModule implements IIntegrationModule {

	public abstract String getModId();

	@Override
	public String name() {
		return getModId() + " (mod) CC integration module";
	}

	@Override
	public boolean canLoad() {
		final String modId = getModId();
		return Loader.isModLoaded(modId);
	}
}
