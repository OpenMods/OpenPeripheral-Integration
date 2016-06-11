package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

public class FluidContainerMetaProvider extends ItemStackMetaProviderSimple<IFluidContainerItem> {

	@Override
	public String getKey() {
		return "fluid_container";
	}

	@Override
	public Object getMeta(IFluidContainerItem target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("contents", target.getFluid(stack));
		map.put("capacity", target.getCapacity(stack));
		return map;
	}

}
