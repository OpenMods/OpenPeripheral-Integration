package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import openperipheral.api.IItemStackMetaProvider;

import com.google.common.collect.Maps;

public class OreDictMetaProvider implements IItemStackMetaProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "ore_dict";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		int[] oreIds = OreDictionary.getOreIDs(stack);

		Map<String, Boolean> result = Maps.newHashMap();

		for (int oreId : oreIds)
			result.put(OreDictionary.getOreName(oreId), true);

		return result;
	}

}
