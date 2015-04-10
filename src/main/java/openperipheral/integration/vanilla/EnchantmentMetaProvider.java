package openperipheral.integration.vanilla;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

public class EnchantmentMetaProvider implements IItemStackCustomMetaProvider<Item>  {

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

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		return tag != null && tag.hasKey("ench", Constants.NBT.TAG_LIST);
	}
}
