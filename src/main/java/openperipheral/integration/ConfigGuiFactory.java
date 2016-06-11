package openperipheral.integration;

import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

@SuppressWarnings({ "rawtypes", "unchecked" })
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
