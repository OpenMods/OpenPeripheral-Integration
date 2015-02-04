package openperipheral.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import openperipheral.api.meta.IItemStackMetaProvider;

public class BurnTimeMetaProvider implements IItemStackMetaProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "burn_time";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		int time = TileEntityFurnace.getItemBurnTime(stack);
		return time > 0? time : null;
	}
}
