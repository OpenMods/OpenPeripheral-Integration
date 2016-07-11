package openperipheral.integration;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.IConfigElement;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import openmods.config.gui.OpenModsConfigScreen;

public class ConfigGuiFactory implements IModGuiFactory {

	public static class ConfigScreen extends OpenModsConfigScreen {
		@SuppressWarnings({ "rawtypes" })
		private static List<IConfigElement> createConfigElements() {
			ConfigCategory modules = OpenPeripheralIntegration.instance.config().getCategory(OpenPeripheralIntegration.CATEGORY_MODULES);
			modules.setLanguageKey("openperipheralintegration.config.modules");
			final ConfigElement modulesElement = new ConfigElement(modules);
			return Lists.<IConfigElement> newArrayList(modulesElement);
		}

		public ConfigScreen(GuiScreen parent) {
			super(parent, OpenPeripheralIntegration.MOD_ID, OpenPeripheralIntegration.MOD_ID, createConfigElements());
		}
	}

	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ConfigScreen.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return ImmutableSet.of();
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

}
