package openperipheral.integration.mystcraft;

import java.util.Collection;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.IItemStackMetadataProvider;

import com.google.common.collect.Maps;

public class BookMetaProvider implements IItemStackMetadataProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "myst-book";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		final String unlocalizedName = target.getUnlocalizedName();
		if ("item.myst.linkbook".equals(unlocalizedName) || "item.myst.agebook".equals(unlocalizedName)) {
			NBTTagCompound tag = stack.getTagCompound();

			if (tag != null) {
				Map<String, Object> result = Maps.newHashMap();

				result.put("destination", tag.getString("agename"));
				addLinkingBookFlags(result, tag);
				addCoordinates(result, tag);
			}
		}
		return null;
	}

	private static void addCoordinates(Map<String, Object> map, NBTTagCompound tag) {
		Map<Integer, Integer> pos = Maps.newHashMap();
		map.put("spawn", pos);
		pos.put(1, tag.getInteger("SpawnX"));
		pos.put(2, tag.getInteger("SpawnY"));
		pos.put(3, tag.getInteger("SpawnZ"));
		map.put("spawnYaw", tag.getFloat("SpawnYaw"));
	}

	private static void addLinkingBookFlags(Map<String, Object> map, NBTTagCompound tag) {
		Map<String, Boolean> flags = Maps.newHashMap();

		if (tag.hasKey("Flags")) {
			@SuppressWarnings("unchecked")
			Collection<NBTBase> tags = tag.getCompoundTag("Flags").getTags();
			for (NBTBase s : tags) {
				flags.put(s.getName(), Boolean.TRUE);
			}
		}

		map.put("flags", flags);
	}
}
