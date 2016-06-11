package openperipheral.integration.appeng;

import appeng.api.AEApi;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

public class StorageCellMetaProvider implements IItemStackCustomMetaProvider<Item> {

	@Override
	public String getKey() {
		return "me_cell";
	}

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		return AEApi.instance().registries().cell().isCellHandled(stack);
	}

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

}
