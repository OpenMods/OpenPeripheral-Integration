package openperipheral.integration.railcraft;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.registry.GameRegistry;

@SuppressWarnings("serial")
public class TicketMetaProvider extends ItemStackMetaProviderSimple<Item> {

	private final ItemStack ticketStack;

	public TicketMetaProvider() {
		ticketStack = GameRegistry.findItemStack("Railcraft", "routing.ticket", 1);
		Preconditions.checkNotNull(ticketStack, "Can't find ticket item");
	}

	@Override
	public String getKey() {
		return "rail-ticket";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (stack.isItemEqual(ticketStack)) {
			NBTTagCompound tag = stack.stackTagCompound;
			if (tag != null) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("owner", tag.getString("owner"));
				map.put("dest", tag.getString("dest"));
				return map;
			}
		}
		return null;
	}

}
