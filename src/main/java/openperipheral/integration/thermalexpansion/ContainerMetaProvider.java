package openperipheral.integration.thermalexpansion;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetadataProvider;
import cofh.api.item.IInventoryContainerItem;

import com.google.common.collect.Maps;

public class ContainerMetaProvider implements IItemStackMetadataProvider<IInventoryContainerItem> {

	@Override
	public Class<? extends IInventoryContainerItem> getTargetClass() {
		return IInventoryContainerItem.class;
	}

	@Override
	public String getKey() {
		return "container";
	}

	@Override
	public Object getMeta(IInventoryContainerItem target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("sizeInventory", target.getSizeInventory(stack));
		map.put("inventoryContents", target.getInventoryContents(stack));
		return map;
	}

}
