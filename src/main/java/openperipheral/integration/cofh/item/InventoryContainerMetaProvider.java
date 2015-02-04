package openperipheral.integration.cofh.item;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import cofh.api.item.IInventoryContainerItem;

import com.google.common.collect.Maps;

public class InventoryContainerMetaProvider extends ItemStackMetaProviderSimple<IInventoryContainerItem> {

	@Override
	public String getKey() {
		return "container";
	}

	@Override
	public Object getMeta(IInventoryContainerItem target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("size", target.getSizeInventory(stack));
		return map;
	}

}
