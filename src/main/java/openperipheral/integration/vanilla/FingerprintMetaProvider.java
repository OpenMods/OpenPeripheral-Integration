package openperipheral.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import openmods.utils.ItemUtils;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

public class FingerprintMetaProvider extends ItemStackMetaProviderSimple<Item>{
    private static final String TAG_NBT = "nbt_hash";

    @Override
    public Object getMeta(Item target, ItemStack stack) {
        if (stack.hasTagCompound()) {
            return ItemUtils.getNBTHash(stack.getTagCompound());
        }

        return null;
    }

    @Override
    public String getKey() {
        return TAG_NBT;
    }
}
