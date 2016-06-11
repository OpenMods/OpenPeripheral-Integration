package openperipheral.integration.mystcraft.v2;

import com.google.common.collect.Maps;
import com.xcompwiz.mystcraft.api.hook.PageAPI;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

public class PageMetaProvider implements IItemStackCustomMetaProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		final PageAPI pageApi = MystcraftAccess.pageApi;
		return pageApi != null && pageApi.getPageSymbol(stack) != null;
	}

	@Override
	public String getKey() {
		return "symbol";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (MystcraftAccess.pageApi == null) return null;

		Map<String, Object> result = Maps.newHashMap();
		String id = MystcraftAccess.pageApi.getPageSymbol(stack);
		if (id == null) return null;

		result.put("id", id);

		if (MystcraftAccess.symbolApi != null) {
			final IAgeSymbol symbol = MystcraftAccess.symbolApi.getSymbol(id);
			if (symbol != null) {
				result.put("name", symbol.displayName());
				result.put("poem", symbol.getPoem());
			}
		}

		return result;
	}
}
