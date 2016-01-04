package openperipheral.integration;

import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.google.common.collect.ImmutableSet;

@SuppressWarnings({ })
public class ConfigGuiFactory implements IModGuiFactory {

	public static class ConfigScreen extends GuiConfig {

		public ConfigScreen(GuiScreen parent) {
			super(parent, createConfigElements(), OpenPeripheralIntegration.MOD_ID, true, true, "Enabled modules");
		}

		private static List<IConfigElement> createConfigElements() {
			ConfigCategory modules = OpenPeripheralIntegration.instance.config().getCategory(OpenPeripheralIntegration.CATEGORY_MODULES);
			return new ConfigElement(modules).getChildElements();
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
