package openperipheral.integration.computercraft;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.IItemStackMetaProvider;

import com.google.common.collect.Maps;

public class PrintoutMetaProvider implements IItemStackMetaProvider<Object> {

	@Override
	public Class<?> getTargetClass() {
		return ModuleComputerCraft.PRINTOUT_CLASS.get();
	}

	@Override
	public String getKey() {
		return "printout";
	}

	@Override
	public Object getMeta(Object target, ItemStack stack) {
		Map<String, Object> printoutMap = Maps.newHashMap();

		printoutMap.put("title", ReflectionHelper.call(target, "getTitle", stack));
		printoutMap.put("pages", ReflectionHelper.call(target, "getPageCount", stack));
		String[] texts = ReflectionHelper.call(target, "getText", stack);

		printoutMap.put("text", texts);

		return printoutMap;
	}

}
