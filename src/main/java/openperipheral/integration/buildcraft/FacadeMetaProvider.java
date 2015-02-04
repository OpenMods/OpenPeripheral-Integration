package openperipheral.integration.buildcraft;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import buildcraft.api.facades.IFacadeItem;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FacadeMetaProvider extends ItemStackMetaProviderSimple<IFacadeItem> {

	@Override
	public Object getMeta(IFacadeItem target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();

		result.put("type", target.getFacadeType(stack));

		Block[] blocks = target.getBlocksForFacade(stack);
		int[] meta = target.getMetaValuesForFacade(stack);

		List<Map<String, Object>> states = Lists.newArrayList();
		for (int i = 0; i < blocks.length; i++) {
			Map<String, Object> state = Maps.newHashMap();
			state.put("block", blocks[i].getUnlocalizedName());
			state.put("meta", meta[i]);
			states.add(state);
		}
		result.put("states", states);

		return result;
	}

	@Override
	public String getKey() {
		return "facade";
	}

}
