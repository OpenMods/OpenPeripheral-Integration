package openperipheral.integration.cofh.energy;

import cofh.api.energy.IEnergyContainerItem;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

public class EnergyMetaProvider extends ItemStackMetaProviderSimple<IEnergyContainerItem> {

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
