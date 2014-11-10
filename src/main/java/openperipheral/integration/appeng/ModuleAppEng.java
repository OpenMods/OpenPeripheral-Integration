package openperipheral.integration.appeng;

import openmods.Mods;
import openperipheral.integration.OPIntegrationModule;

public class ModuleAppEng extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.APPLIEDENERGISTICS;
	}

	@Override
	public void load() {
		// TODO reeimplement from base (new API)
	}
}
