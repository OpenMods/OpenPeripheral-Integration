package openperipheral.integration;

import net.minecraftforge.common.Configuration;
import openmods.config.ConfigProcessing;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "OpenPeripheralIntegration", name = "OpenPeripheralIntegration", version = "@VERSION@", dependencies = "required-after:OpenMods@[0.5.1,];required-after:ComputerCraft@[1.60,];required-after:OpenPeripheralCore")
public class OpenPeripheralIntegration {

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		Configuration config = new Configuration(evt.getSuggestedConfigurationFile());
		ConfigProcessing.processAnnotations(evt.getSuggestedConfigurationFile(), "OpenPeripheralIntegration", config, Config.class);
		if (config.hasChanged()) config.save();
	}

}
