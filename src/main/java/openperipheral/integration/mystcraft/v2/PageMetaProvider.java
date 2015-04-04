package openperipheral.integration.mystcraft.v2;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.meta.IItemStackMetaProvider;

import com.google.common.collect.Maps;
import com.xcompwiz.mystcraft.api.MystAPI;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

public class PageMetaProvider implements IItemStackMetaProvider<Item> {

	private final MystAPI api;

	private final Class<?> CLS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.item.ItemPage");

	private final Class<?> HELPER_CLS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.page.Page");

	private final Function1<String, ItemStack> GET_SYMBOL = MethodAccess.create(String.class, HELPER_CLS, ItemStack.class, "getSymbol");

	public PageMetaProvider(MystAPI api) {
		this.api = api;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends Item> getTargetClass() {
		return (Class<? extends Item>)CLS;
	}

	@Override
	public String getKey() {
		return "symbol";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();
		String id = GET_SYMBOL.call(null, stack);
		result.put("id", id);

		final IAgeSymbol symbol = api.getSymbolAPI().getSymbol(id);
		if (symbol != null) {
			result.put("name", symbol.displayName());
			result.put("poem", symbol.getPoem());
		}

		return result;
	}
}
