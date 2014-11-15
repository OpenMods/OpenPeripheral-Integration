package openperipheral.integration.thaumcraft;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import thaumcraft.api.wands.IWandFocus;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class WandFocusMetaProvider extends ItemStackMetaProviderSimple<IWandFocus> {

	@Override
	public String getKey() {
		return "wand_focus";
	}

	@Override
	public Object getMeta(IWandFocus target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();

		result.put("cost", target.getVisCost());
		result.put("isPerTick", target.isVisCostPerTick());
		result.put("color", target.getFocusColor());

		return result;
	}

}
