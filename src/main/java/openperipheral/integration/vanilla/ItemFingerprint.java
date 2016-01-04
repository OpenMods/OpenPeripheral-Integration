package openperipheral.integration.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import openmods.utils.ItemUtils;

/**
 * SearchNeedle is a helper class meant to allow easier communication between
 * Lua and vanilla requesting access to a specific item;
 */
public class ItemFingerprint {

	public final ResourceLocation id;
	public final int damage;
	public final String nbtHash;

	public ItemFingerprint(String id, int damage, String nbtHash) {
		this.id = new ResourceLocation(id);
		this.damage = damage;
		this.nbtHash = nbtHash;
	}

	public ItemFingerprint(Item item, int damage, NBTTagCompound tag) {
		this.id = GameData.getItemRegistry().getNameForObject(item);
		this.damage = damage;
		this.nbtHash = tag != null? ItemUtils.getNBTHash(tag) : null;
	}

	public ItemFingerprint(ItemStack stack) {
		this.id = GameData.getItemRegistry().getNameForObject(stack.getItem());
		this.damage = stack.getItemDamage();

		NBTTagCompound tag = stack.getTagCompound();
		this.nbtHash = tag != null? ItemUtils.getNBTHash(tag) : null;
	}

	@Override
	public String toString() {
		return String.format("%s:%d:%s", id, damage, nbtHash);
	}
}
