package openperipheral.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import openperipheral.api.IItemStackMetadataProvider;

public class EnchantmentMetaProvider implements IItemStackMetadataProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "ench";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		NBTTagList ench = stack.getEnchantmentTagList();
		return ench != null? ModuleVanilla.listEnchantments(ench) : null;
	}

}
