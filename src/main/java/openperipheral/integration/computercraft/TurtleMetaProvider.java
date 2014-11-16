package openperipheral.integration.computercraft;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.MethodAccess.Function2;
import openperipheral.api.IItemStackMetaProvider;

import com.google.common.collect.Maps;

import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;

public class TurtleMetaProvider implements IItemStackMetaProvider<Object> {

	private final Class<?> CLASS = ReflectionHelper.getClass("dan200.computercraft.shared.turtle.items.ITurtleItem");

	private final Function1<Integer, ItemStack> GET_FUEL_LEVEL = MethodAccess.create(int.class, CLASS, ItemStack.class, "getFuelLevel");
	private final Function2<ITurtleUpgrade, ItemStack, TurtleSide> GET_UPGRADE = MethodAccess.create(ITurtleUpgrade.class, CLASS, ItemStack.class, TurtleSide.class, "getUpgrade");

	@Override
	public Class<? extends Object> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getKey() {
		return "turtle";
	}

	@Override
	public Object getMeta(Object target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();

		addSideInfo(map, "left", GET_UPGRADE.call(target, stack, TurtleSide.Left));
		addSideInfo(map, "right", GET_UPGRADE.call(target, stack, TurtleSide.Right));

		int fuelLevel = GET_FUEL_LEVEL.call(target, stack);
		map.put("fuel", fuelLevel);

		return map;
	}

	private static void addSideInfo(Map<String, Object> map, String side, ITurtleUpgrade upgrade) {
		if (upgrade != null) {
			Map<Object, Object> upgradeMap = Maps.newHashMap();

			upgradeMap.put("adjective", upgrade.getUnlocalisedAdjective());
			upgradeMap.put("type", upgrade.getType().toString());

			map.put(side, upgradeMap);
		}
	}
}
