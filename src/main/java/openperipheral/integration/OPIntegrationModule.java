package openperipheral.integration;

import openmods.integration.IIntegrationModule;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.Loader;

public abstract class OPIntegrationModule implements IIntegrationModule {

	public abstract String getModId();

	@Override
	public String name() {
		return getModId() + " CC integration module";
	}

	@Override
	public boolean canLoad() {
		final String modId = getModId();
		return Loader.isModLoaded(modId) && !ArrayUtils.contains(Config.modBlacklist, modId);
	}
}
