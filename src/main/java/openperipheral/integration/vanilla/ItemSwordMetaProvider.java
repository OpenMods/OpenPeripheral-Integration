package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

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
