package openperipheral.integration.thaumcraft;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import thaumcraft.api.IRunicArmor;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class RunicItemMetaProvider extends ItemStackMetaProviderSimple<IRunicArmor> {

	@Override
	public Object getMeta(IRunicArmor target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("charge", target.getRunicCharge(stack));
		return result;
	}

	@Override
	public String getKey() {
		return "runic";
	}

}
