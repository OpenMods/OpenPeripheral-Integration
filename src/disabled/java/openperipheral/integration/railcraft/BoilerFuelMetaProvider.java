package openperipheral.integration.railcraft;

import java.util.Map;

import mods.railcraft.api.fuel.FuelManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

import com.google.common.collect.Maps;

public class BoilerFuelMetaProvider extends ItemStackMetaProviderSimple<IFluidContainerItem> {

	@Override
	public String getKey() {
		return "boiler_fuel";
	}

	@Override
	public Object getMeta(IFluidContainerItem target, ItemStack stack) {
		FluidStack fluidStack = target.getFluid(stack);
		if (fluidStack != null && fluidStack.amount > 0) {
			int heatPerBucket = FuelManager.getBoilerFuelValue(fluidStack.getFluid());
			if (heatPerBucket > 0) {
				Map<String, Number> result = Maps.newHashMap();
				result.put("total", heatPerBucket / 1000.0f * fluidStack.amount);
				result.put("per_bucket", heatPerBucket);
				return result;
			}
		}

		return null;
	}

}
