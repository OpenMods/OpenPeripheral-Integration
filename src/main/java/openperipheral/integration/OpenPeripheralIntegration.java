package openperipheral.integration;

import net.minecraftforge.common.Configuration;
import openmods.config.ConfigProcessing;
import openmods.integration.Integration;
import openperipheral.integration.appeng.ModuleAppEng;
import openperipheral.integration.buildcraft.ModuleBuildCraft;
import openperipheral.integration.computercraft.ModuleComputerCraft;
import openperipheral.integration.enderstorage.ModuleEnderStorage;
import openperipheral.integration.forestry.ModuleForestry;
import openperipheral.integration.ic2.ModuleIC2;
import openperipheral.integration.minefactoryreloaded.ModuleMinefactoryReloaded;
import openperipheral.integration.mystcraft.ModuleMystcraft;
import openperipheral.integration.projectred.ModuleProjectRed;
import openperipheral.integration.railcraft.ModuleRailcraft;
import openperipheral.integration.sgcraft.ModuleSgCraft;
import openperipheral.integration.thaumcraft.ModuleThaumcraft;
import openperipheral.integration.thermalexpansion.ModuleThermalExpansion;
import openperipheral.integration.tmechworks.ModuleTMechworks;
import openperipheral.integration.vanilla.ModuleVanilla;
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

		Integration.addModule(new ModuleAppEng());
		Integration.addModule(new ModuleBuildCraft());
		Integration.addModule(new ModuleComputerCraft());
		Integration.addModule(new ModuleEnderStorage());
		Integration.addModule(new ModuleForestry());
		Integration.addModule(new ModuleIC2());
		Integration.addModule(new ModuleMinefactoryReloaded());
		Integration.addModule(new ModuleMystcraft());
		Integration.addModule(new ModuleProjectRed());
		Integration.addModule(new ModuleRailcraft());
		Integration.addModule(new ModuleSgCraft());
		Integration.addModule(new ModuleThaumcraft());
		Integration.addModule(new ModuleThermalExpansion());
		Integration.addModule(new ModuleTMechworks());
		Integration.addModule(new ModuleVanilla());

	}

}
