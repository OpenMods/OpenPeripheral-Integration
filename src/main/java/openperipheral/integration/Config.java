package openperipheral.integration;

import openmods.config.properties.ConfigProperty;
import openmods.config.properties.OnLineModifiable;

public class Config {

	@OnLineModifiable
	@ConfigProperty(category = "forestry", name = "showHiddenMutations", comment = "Should show hidden mutations")
	public static boolean showHiddenMutations = false;

	@OnLineModifiable
	@ConfigProperty(category = "forestry", name = "showHiddenBees", comment = "Should show hidden bees")
	public static boolean showHiddenBees = true;

}
