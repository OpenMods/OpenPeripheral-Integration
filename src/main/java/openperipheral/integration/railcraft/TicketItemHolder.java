package openperipheral.integration.railcraft;

import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.item.Item;
import openmods.Mods;

@ObjectHolder(Mods.RAILCRAFT)
public class TicketItemHolder {
	@ObjectHolder("routing.ticket")
	public static final Item ticket = null;

	@ObjectHolder("routing.ticket.gold")
	public static final Item ticketGold = null;
}