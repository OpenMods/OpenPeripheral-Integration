package openperipheral.integration.computercraft;

import static openmods.utils.ReflectionHelper.safeLoad;
import openmods.Mods;
import openmods.utils.SafeClassLoad;
import openperipheral.api.ApiAccess;
import openperipheral.api.IItemStackMetadataBuilder;
import openperipheral.integration.OPIntegrationModule;

public class ModuleComputerCraft extends OPIntegrationModule {
	public static final SafeClassLoad API_CLASS = safeLoad("dan200.computercraft.ComputerCraft");
	public static final SafeClassLoad PRINTOUT_CLASS = safeLoad("dan200.computercraft.shared.media.items.ItemPrintout");
	public static final SafeClassLoad COMPUTER_ITEM_CLASS = safeLoad("dan200.computercraft.shared.computer.items.IComputerItem");
	public static final SafeClassLoad TURTLE_ITEM_CLASS = safeLoad("dan200.computercraft.shared.turtle.items.ITurtleItem");

	@Override
	public String getModId() {
		return Mods.COMPUTERCRAFT;
	}

	@Override
	public void load() {
		PRINTOUT_CLASS.load();
		COMPUTER_ITEM_CLASS.load();
		TURTLE_ITEM_CLASS.load();

		IItemStackMetadataBuilder api = ApiAccess.getApi(IItemStackMetadataBuilder.class);

		api.register(new ComputerMetaProvider());
		api.register(new MediaMetaProvider());
		api.register(new PrintoutMetaProvider());
	}
}
