package openperipheral.integration.railcraft;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

public class TicketMetaProvider implements IItemStackCustomMetaProvider<Item> {

	@Override
	public String getKey() {
		return "rail-ticket";
	}

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		return (target == TicketItemHolder.ticket) || (target == TicketItemHolder.ticketGold);
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("golden", target == TicketItemHolder.ticketGold);

		NBTTagCompound tag = stack.stackTagCompound;
		if (tag != null) {

			map.put("owner", tag.getString("owner"));
			map.put("dest", tag.getString("dest"));
		}
		return map;
	}

}
