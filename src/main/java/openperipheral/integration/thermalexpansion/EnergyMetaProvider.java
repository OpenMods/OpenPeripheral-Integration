package openperipheral.integration.thermalexpansion;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetadataProvider;
import cofh.api.energy.IEnergyContainerItem;

import com.google.common.collect.Maps;

public class EnergyMetaProvider implements IItemStackMetadataProvider<IEnergyContainerItem> {

	@Override
	public Class<? extends IEnergyContainerItem> getTargetClass() {
		return IEnergyContainerItem.class;
	}

	@Override
	public String getKey() {
		return "energy_te";
	}

	@Override
	public Object getMeta(IEnergyContainerItem target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("energyStored", target.getEnergyStored(stack));
		map.put("maxEnergyStored", target.getMaxEnergyStored(stack));
		return map;
	}

}
