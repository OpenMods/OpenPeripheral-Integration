package openperipheral.integration.cofh.item;

import cofh.api.item.IInventoryContainerItem;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

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
