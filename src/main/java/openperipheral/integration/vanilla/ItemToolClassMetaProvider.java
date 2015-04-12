package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

import com.google.common.collect.Maps;

public class ItemToolClassMetaProvider implements IItemStackCustomMetaProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "tool_class";
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		return !target.getToolClasses(stack).isEmpty();
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		final Map<String, Object> result = Maps.newHashMap();
		for (String tool : target.getToolClasses(stack))
			result.put(tool, target.getHarvestLevel(stack, tool));

		return result;
	}

}
