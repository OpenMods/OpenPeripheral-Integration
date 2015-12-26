package openperipheral.integration;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import openmods.Log;
import openmods.Mods;
import openmods.integration.Integration;
import openperipheral.integration.appeng.ModuleAppEng;
import openperipheral.integration.buildcraft.ModuleBuildCraftFacades;
import openperipheral.integration.buildcraft.ModuleBuildCraftTile;
import openperipheral.integration.buildcraft.ModuleBuildCraftTransport;
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
import openperipheral.integration.minefactoryreloaded.ModuleMinefactoryReloaded;
import openperipheral.integration.minefactoryreloaded.ModuleMinefactoryReloadedStorage;
import openperipheral.integration.mystcraft.v1.ModuleMystcraftV1;
import openperipheral.integration.mystcraft.v2.ModuleMystcraftV2;
import openperipheral.integration.railcraft.ModuleRailcraft;
import openperipheral.integration.railcraft.ModuleRailcraftCarts;
import openperipheral.integration.railcraft.ModuleRailcraftFuel;
import openperipheral.integration.thaumcraft.ModuleThaumcraft;
import openperipheral.integration.thaumcraft.ModuleThaumcraftApi;
import openperipheral.integration.thermalexpansion.ModuleThermalExpansion;
import openperipheral.integration.tmechworks.ModuleTMechworks;
import openperipheral.integration.vanilla.ModuleVanilla;
import openperipheral.integration.vanilla.ModuleVanillaInventory;
import openperipheral.integration.vanilla.ModuleVanillaInventoryManipulation;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;

@Mod(modid = OpenPeripheralIntegration.MOD_ID,
		name = OpenPeripheralIntegration.MOD_ID,
		version = "$VERSION$",
		guiFactory = "openperipheral.integration.ConfigGuiFactory",
		acceptableRemoteVersions = "*",
		dependencies = "required-after:OpenMods@[$LIB-VERSION$,$NEXT-LIB-VERSION$);required-after:OpenPeripheralApi@$OP-API-VERSION$;after:ComputerCraft@[1.70,];after:appliedEnergistics-2;after:IC2;after:EnderStorage;after:BuildCraft|Core;after:Forestry;after:Mystcraft;after:Railcraft;after:Thaumcraft;after:ThermalExpansion;")
public class OpenPeripheralIntegration {

	public class ConfigChangeListener {
		@SubscribeEvent
		public void onConfigChange(OnConfigChangedEvent evt) {
			if (MOD_ID.equals(evt.modID)) config.save();
		}
	}

	public static final String MOD_ID = "OpenPeripheralIntegration";

	public static final String CATEGORY_MODULES = "modules";

	private static boolean checkConfig(Configuration config, String value) {
		final Property property = config.get(CATEGORY_MODULES, value, true);
		property.setRequiresMcRestart(true);
		return property.getBoolean();
	}

	private static boolean sameOrNewerVersion(String modid, String version) {
		final ModContainer modContainer = Loader.instance().getIndexedModList().get(modid);
		if (modContainer == null) return true;

		final ArtifactVersion targetVersion = new DefaultArtifactVersion(version);
		final ArtifactVersion actualVersion = modContainer.getProcessedVersion();
		return actualVersion.compareTo(targetVersion) >= 0;
	}

	@Instance(MOD_ID)
	public static OpenPeripheralIntegration instance;

	Configuration config;

	public Configuration config() {
		return config;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		config = new Configuration(evt.getSuggestedConfigurationFile());

		if (config.hasCategory("integration")) {
			ConfigCategory integration = config.getCategory("integration");
			config.removeCategory(integration);
		}

		if (checkConfig(config, "vanilla")) Integration.addModule(new ModuleVanilla());
		if (checkConfig(config, "vanilla-inventory")) Integration.addModule(new ModuleVanillaInventory());
		if (checkConfig(config, "vanilla-inventory-manipulation")) Integration.addModule(new ModuleVanillaInventoryManipulation());

		if (checkConfig(config, "cofh-api-energy")) Integration.addModule(new ModuleCofhEnergy());
		if (checkConfig(config, "cofh-api-inventory")) Integration.addModule(new ModuleCofhInventory());
		if (checkConfig(config, "cofh-api-item")) Integration.addModule(new ModuleCofhItem());
		if (checkConfig(config, "cofh-api-tileentity")) Integration.addModule(new ModuleCofhTileEntity());
		if (checkConfig(config, "cofh-api-transport")) Integration.addModule(new ModuleCofhTransport());

		if (checkConfig(config, "buildcraft-api-transport")) Integration.addModule(new ModuleBuildCraftTransport());
		if (checkConfig(config, "buildcraft-api-tilenentity")) Integration.addModule(new ModuleBuildCraftTile());
		if (checkConfig(config, "buildcraft-api-facades")) Integration.addModule(new ModuleBuildCraftFacades());

		if (checkConfig(config, "ic2-mod")) Integration.addModule(new ModuleIC2());
		if (checkConfig(config, "ic2-api")) Integration.addModule(new ModuleIC2Api());

		if (checkConfig(config, "railcraft-mod")) Integration.addModule(new ModuleRailcraft());
		if (checkConfig(config, "railcraft-api-carts")) Integration.addModule(new ModuleRailcraftCarts());
		if (checkConfig(config, "railcraft-api-fuel")) Integration.addModule(new ModuleRailcraftFuel());

		if (checkConfig(config, "thaumcraft-mod")) Integration.addModule(new ModuleThaumcraft());
		if (checkConfig(config, "thaumcraft-api")) Integration.addModule(new ModuleThaumcraftApi());

		if (checkConfig(config, "mfr-mod")) Integration.addModule(new ModuleMinefactoryReloaded());
		if (checkConfig(config, "mfr-mod-storage")) Integration.addModule(new ModuleMinefactoryReloadedStorage());

		if (checkConfig(config, "computercraft-mod")) Integration.addModule(new ModuleComputerCraft());
		if (checkConfig(config, "enderstorage-mod")) Integration.addModule(new ModuleEnderStorage());
		if (checkConfig(config, "forestry-mod")) Integration.addModule(new ModuleForestry());
		if (checkConfig(config, "mystcraft-mod")) {
			if (sameOrNewerVersion(Mods.MYSTCRAFT, "0.11.1.00")) {
				if (sameOrNewerVersion(Mods.MYSTCRAFT, "0.11.6.02")) {
					FMLInterModComms.sendMessage(Mods.MYSTCRAFT, "API", "openperipheral.integration.mystcraft.v2.MystcraftAccess.init");
					Integration.addModule(new ModuleMystcraftV2());
				} else {
					Log.warn("Unsupported Mystcraft version!");
				}
			} else {
				Log.warn("Using old Mystcraft integration, things may explode");
				Integration.addModule(new ModuleMystcraftV1());
			}
		}
		if (checkConfig(config, "thaumcraft-mod")) Integration.addModule(new ModuleThaumcraft());
		if (checkConfig(config, "tmechworks-mod")) Integration.addModule(new ModuleTMechworks());
		if (checkConfig(config, "thermalexpansion-mod")) Integration.addModule(new ModuleThermalExpansion());
		if (checkConfig(config, "ae2-mod")) Integration.addModule(new ModuleAppEng());

		FMLCommonHandler.instance().bus().register(new ConfigChangeListener());

		if (config.hasChanged()) config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		OpcAccess.checkApiPresent();
	}

}
