package openperipheral.integration.mystcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.IItemStackMetadataProvider;

public class PageMetaProvider implements IItemStackMetadataProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "symbol";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if ("item.myst.page".equals(target.getUnlocalizedName())) {
			NBTTagCompound tag = stack.getTagCompound();
			if (tag != null) return tag.getString("symbol");
		}

		return null;
	}

}
