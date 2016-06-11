package openperipheral.integration.cofh.item;

import cofh.api.item.IAugmentItem;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

public class AugumenedItemMetaProvider extends ItemStackMetaProviderSimple<IAugmentItem> {

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
