package openperipheral.integration.cofh.item;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetadataProvider;
import cofh.api.item.IAugmentItem;

import com.google.common.collect.Maps;

public class AugumenedItemMetaProvider implements IItemStackMetadataProvider<IAugmentItem> {

	@Override
	public Class<? extends IAugmentItem> getTargetClass() {
		return IAugmentItem.class;
	}

	@Override
	public String getKey() {
		return "augumented";
	}

	@Override
	public Object getMeta(IAugmentItem target, ItemStack stack) {
		Map<String, Integer> result = Maps.newHashMap();

		for (String type : target.getAugmentTypes(stack)) {
			int level = target.getAugmentLevel(stack, type);
			result.put(type, level);
		}

		return result;
	}
}
