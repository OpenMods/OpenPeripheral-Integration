package openperipheral.integration.computercraft;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openmods.utils.ReflectionHelper;
import openperipheral.api.IItemStackMetadataProvider;

import com.google.common.collect.Maps;

import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;

public class ComputerMetaProvider implements IItemStackMetadataProvider<Object> {

	@Override
	public Class<? extends Object> getTargetClass() {
		return ModuleComputerCraft.COMPUTER_ITEM_CLASS.get();
	}

	@Override
	public String getKey() {
		return "computer";
	}

	@Override
	public Object getMeta(Object target, ItemStack stack) {
		Map<String, Object> computerInfo = Maps.newHashMap();
		int computerID = ReflectionHelper.call(target, "getComputerID", stack);
		if (computerID >= 0) {
			computerInfo.put("id", computerID);
			String label = ReflectionHelper.call(target, "getLabel", stack);
			if (label != null) computerInfo.put("label", label);
		}

		computerInfo.put("type", ReflectionHelper.call(target, "getFamily", stack));

		if (ModuleComputerCraft.TURTLE_ITEM_CLASS.get().isInstance(target)) addTurtleInfo(computerInfo, stack, (Item)target);

		return computerInfo;
	}

	private static void addTurtleInfo(Map<String, Object> map, ItemStack stack, Item item) {
		addSideInfo(map, "left", ReflectionHelper.<ITurtleUpgrade> call(item, "getUpgrade", stack, TurtleSide.Left));
		addSideInfo(map, "right", ReflectionHelper.<ITurtleUpgrade> call(item, "getUpgrade", stack, TurtleSide.Right));

		int fuelLevel = ReflectionHelper.call(item, "getFuelLevel", stack);
		map.put("fuel", fuelLevel);
	}

	private static void addSideInfo(Map<String, Object> map, String side, ITurtleUpgrade upgrade) {
		if (upgrade != null) {
			Map<Object, Object> upgradeMap = Maps.newHashMap();

			upgradeMap.put("adjective", upgrade.getAdjective());
			upgradeMap.put("type", upgrade.getType().toString());

			map.put(side, upgradeMap);
		}
	}
}
