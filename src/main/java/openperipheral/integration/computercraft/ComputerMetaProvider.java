package openperipheral.integration.computercraft;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemStack;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.meta.IItemStackMetaProvider;

public class ComputerMetaProvider implements IItemStackMetaProvider<Object> {

	private final Class<?> CLASS = ReflectionHelper.getClass("dan200.computercraft.shared.computer.items.IComputerItem");

	private final Function1<Integer, ItemStack> GET_COMPUTER_ID = MethodAccess.create(int.class, CLASS, ItemStack.class, "getComputerID");
	private final Function1<String, ItemStack> GET_LABEL = MethodAccess.create(String.class, CLASS, ItemStack.class, "getLabel");
	private final Function1<Object, ItemStack> GET_FAMILY = MethodAccess.create(Object.class, CLASS, ItemStack.class, "getFamily");

	@Override
	public Class<? extends Object> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getKey() {
		return "computer";
	}

	@Override
	public Object getMeta(Object target, ItemStack stack) {
		Map<String, Object> computerInfo = Maps.newHashMap();
		int computerID = GET_COMPUTER_ID.call(target, stack);
		if (computerID >= 0) {
			computerInfo.put("id", computerID);
			String label = GET_LABEL.call(target, stack);
			computerInfo.put("label", Strings.nullToEmpty(label));
		}

		computerInfo.put("type", GET_FAMILY.call(target, stack));

		return computerInfo;
	}
}
