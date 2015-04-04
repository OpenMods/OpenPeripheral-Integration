package openperipheral.integration.mystcraft.v1;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

public class PageMetaProvider extends ItemStackMetaProviderSimple<Item> {

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
