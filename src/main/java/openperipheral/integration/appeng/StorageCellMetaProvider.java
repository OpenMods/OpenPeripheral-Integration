package openperipheral.integration.appeng;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import appeng.api.AEApi;
import appeng.api.storage.*;

import com.google.common.collect.Maps;

public class StorageCellMetaProvider extends ItemStackMetaProviderSimple<Item> {

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		IMEInventoryHandler<?> inventory = AEApi.instance().registries().cell().getCellInventory(stack, null, StorageChannel.ITEMS);// get the inventory handler from ae api
		if (inventory instanceof ICellInventoryHandler) {
			ICellInventoryHandler handler = (ICellInventoryHandler)inventory;
			ICellInventory cellInventory = handler.getCellInv();
			if (cellInventory != null) {
				Map<String, Object> ret = Maps.newHashMap();

				ret.put("preformatted", handler.isPreformatted());
				ret.put("fuzzy", handler.isFuzzy());
				ret.put("freeBytes", cellInventory.getFreeBytes());
				ret.put("totalBytes", cellInventory.getTotalBytes());
				ret.put("usedBytes", cellInventory.getUsedBytes());
				ret.put("totalTypes", cellInventory.getTotalItemTypes());
				ret.put("usedTypes", cellInventory.getStoredItemTypes());
				ret.put("freeTypes", cellInventory.getRemainingItemTypes());

				return ret;
			}
		}

		return null;
	}

	@Override
	public String getKey() {
		return "me_cell";
	}

}
