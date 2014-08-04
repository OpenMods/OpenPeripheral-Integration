package openperipheral.integration.appeng;

import java.util.List;

import net.minecraft.item.ItemStack;
import openperipheral.api.ITypeConverter;
import openperipheral.api.ITypeConvertersRegistry;
import appeng.api.IItemList;

public class ConverterIItemList implements ITypeConverter {

	@Override
	public Object fromLua(ITypeConvertersRegistry registry, Object obj, Class<?> expected) {
		return null;
	}

	@Override
	public Object toLua(ITypeConvertersRegistry registry, Object obj) {
		List<ItemStack> items = ((IItemList)obj).getItems();
		return (obj instanceof IItemList)? registry.toLua(items) : null;
	}

}
