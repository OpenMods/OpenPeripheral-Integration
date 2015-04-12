package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

import com.google.common.collect.Maps;

public class ItemSwordMetaProvider extends ItemStackMetaProviderSimple<ItemSword> {

	@Override
	public String getKey() {
		return "sword";
	}

	@Override
	public Object getMeta(ItemSword target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("material", target.getToolMaterialName());
		return result;
	}

}
