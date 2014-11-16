package openperipheral.integration.computercraft;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.IItemStackMetaProvider;

import com.google.common.collect.Maps;

public class PrintoutMetaProvider implements IItemStackMetaProvider<Object> {

	private final Class<?> CLASS = ReflectionHelper.getClass("dan200.computercraft.shared.media.items.ItemPrintout");

	private final Function1<String, ItemStack> GET_TITLE = MethodAccess.create(String.class, CLASS, ItemStack.class, "getTitle");
	private final Function1<Integer, ItemStack> GET_PAGE_COUNT = MethodAccess.create(int.class, CLASS, ItemStack.class, "getPageCount");
	private final Function1<String[], ItemStack> GET_TEXT = MethodAccess.create(String[].class, CLASS, ItemStack.class, "getText");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getKey() {
		return "printout";
	}

	@Override
	public Object getMeta(Object target, ItemStack stack) {
		Map<String, Object> printoutMap = Maps.newHashMap();

		printoutMap.put("title", GET_TITLE.call(target, stack));
		printoutMap.put("pages", GET_PAGE_COUNT.call(target, stack));
		String[] texts = GET_TEXT.call(target, stack);

		printoutMap.put("text", texts);

		return printoutMap;
	}

}
