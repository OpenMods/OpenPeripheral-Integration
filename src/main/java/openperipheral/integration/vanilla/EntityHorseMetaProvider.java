package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.Vec3;
import openmods.utils.InventoryUtils;
import openmods.utils.ReflectionHelper;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityHorseMetaProvider implements IEntityMetadataProvider<EntityHorse> {

	@Override
	public Class<? extends EntityHorse> getTargetClass() {
		return EntityHorse.class;
	}

	@Override
	public String getKey() {
		return "horse";
	}

	@Override
	public Object getMeta(EntityHorse target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		map.put("eatingHaystack", target.isEatingHaystack());
		map.put("hasReproduced", target.getHasReproduced());
		map.put("bred", target.func_110205_ce());
		map.put("horseType", target.getHorseType());
		map.put("horseVariant", target.getHorseVariant());
		map.put("horseTemper", target.getTemper());
		map.put("horseTame", target.isTame());
		map.put("ownerUUID", target.func_152119_ch());

		final boolean chested = target.isChested();
		map.put("chestedHorse", chested);
		if (chested) {
			IInventory invent = (IInventory)ReflectionHelper.getProperty("", target, "horseChest", "field_110296_bG");
			map.put("chest", InventoryUtils.getAllItems(invent));
		}

		return map;
	}

}
