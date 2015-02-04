package openperipheral.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openmods.utils.ItemUtils;
import openperipheral.api.meta.IItemStackMetaProvider;

public class FingerprintMetaProvider implements IItemStackMetaProvider<Item> {
	private static final String TAG_NBT = "nbt_hash";

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		final NBTTagCompound tag = stack.getTagCompound();
		return tag != null? ItemUtils.getNBTHash(tag) : null;
	}

	@Override
	public String getKey() {
		return TAG_NBT;
	}
}
