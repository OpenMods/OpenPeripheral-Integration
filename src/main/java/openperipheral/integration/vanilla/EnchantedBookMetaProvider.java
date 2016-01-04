package openperipheral.integration.vanilla;

import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

public class EnchantedBookMetaProvider extends ItemStackMetaProviderSimple<ItemEnchantedBook> {

	@Override
	public String getKey() {
		return "enchanted_book";
	}

	@Override
	public Object getMeta(ItemEnchantedBook target, ItemStack stack) {
		NBTTagList ench = target.getEnchantments(stack);
		return ench != null? ModuleVanilla.listEnchantments(ench) : null;
	}

}
