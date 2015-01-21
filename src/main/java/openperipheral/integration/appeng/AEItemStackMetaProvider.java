package openperipheral.integration.appeng;

import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import openperipheral.integration.OpenPeripheralIntegration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AEItemStackMetaProvider extends ItemStackMetaProviderSimple<Item> {
	// Currently the only additional data we are interested in is whether the
	// item is craftable by the network.
	public static final String IS_CRAFTABLE_SUFFIX = "-IsCraftable";

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt.hasKey(OpenPeripheralIntegration.MOD_ID + IS_CRAFTABLE_SUFFIX)) { return nbt.getBoolean(OpenPeripheralIntegration.MOD_ID + IS_CRAFTABLE_SUFFIX); }
		}

		return null;
	}

	@Override
	public String getKey() {
		return "is_craftable";
	}
}
