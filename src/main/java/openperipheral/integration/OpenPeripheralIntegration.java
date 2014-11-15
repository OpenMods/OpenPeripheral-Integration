package openperipheral.integration;

import net.minecraftforge.common.config.Configuration;
import openmods.config.properties.ConfigProcessing;
import openmods.integration.Integration;
import openperipheral.integration.buildcraft.*;
import openperipheral.integration.cofh.energy.ModuleCofhEnergy;
import openperipheral.integration.cofh.inventory.ModuleCofhInventory;
import openperipheral.integration.cofh.item.ModuleCofhItem;
import openperipheral.integration.cofh.tileentity.ModuleCofhTileEntity;
import openperipheral.integration.cofh.transport.ModuleCofhTransport;
import openperipheral.integration.computercraft.ModuleComputerCraft;
import openperipheral.integration.enderstorage.ModuleEnderStorage;
import openperipheral.integration.forestry.ModuleForestry;
import openperipheral.integration.ic2.ModuleIC2;
import openperipheral.integration.ic2.ModuleIC2Api;
import openperipheral.integration.mystcraft.ModuleMystcraft;
import openperipheral.integration.railcraft.ModuleRailcraft;
import openperipheral.integration.railcraft.ModuleRailcraftCarts;
import openperipheral.integration.thaumcraft.ModuleThaumcraft;
import openperipheral.integration.thaumcraft.ModuleThaumcraftApi;
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

		// Integration.addModule(new ModuleAppEng());
		//
		// Integration.addModule(new ModuleMinefactoryReloaded());

		Integration.addModule(new ModuleVanilla());

		Integration.addModule(new ModuleCofhEnergy());
		Integration.addModule(new ModuleCofhInventory());
		Integration.addModule(new ModuleCofhItem());
		Integration.addModule(new ModuleCofhTileEntity());
		Integration.addModule(new ModuleCofhTransport());

		Integration.addModule(new ModuleBuildCraftPower());
		Integration.addModule(new ModuleBuildCraftTransport());
		Integration.addModule(new ModuleBuildCraftTile());
		Integration.addModule(new ModuleBuildCraftFacades());

		Integration.addModule(new ModuleIC2());
		Integration.addModule(new ModuleIC2Api());

		Integration.addModule(new ModuleRailcraft());
		Integration.addModule(new ModuleRailcraftCarts());

		Integration.addModule(new ModuleThaumcraft());
		Integration.addModule(new ModuleThaumcraftApi());

		Integration.addModule(new ModuleComputerCraft());
		Integration.addModule(new ModuleEnderStorage());
		Integration.addModule(new ModuleForestry());
		Integration.addModule(new ModuleMystcraft());
		Integration.addModule(new ModuleThaumcraft());
		Integration.addModule(new ModuleTMechworks());
	}

}
