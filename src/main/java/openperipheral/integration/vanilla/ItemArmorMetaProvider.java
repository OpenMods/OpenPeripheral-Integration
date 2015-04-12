package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

import com.google.common.collect.Maps;

public class ItemArmorMetaProvider extends ItemStackMetaProviderSimple<ItemArmor> {

	@Override
	public String getKey() {
		return "armor";
	}

	@Override
	public Object getMeta(ItemArmor target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("material", target.getArmorMaterial());
		result.put("type", convertArmorType(target.armorType));

		final int color = target.getColor(stack);
		if (color >= 0) result.put("color", color);

		return result;
	}

	private static String convertArmorType(int armorType) {
		switch (armorType) {
			case 0:
				return "helmet";
			case 1:
				return "plate";
			case 2:
				return "legs";
			case 3:
				return "boots";
			default:
				return "unknown";
		}
	}

}
