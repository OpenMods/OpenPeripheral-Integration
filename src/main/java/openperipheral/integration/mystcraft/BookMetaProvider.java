package openperipheral.integration.mystcraft;

import java.util.Collection;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openmods.Log;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

import com.google.common.collect.Maps;

public class BookMetaProvider extends ItemStackMetaProviderSimple<Item> {

	@Override
	public String getKey() {
		return "myst_book";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		final String unlocalizedName = target.getUnlocalizedName();
		final boolean isLinkbook = "item.myst.linkbook".equals(unlocalizedName);
		final boolean isAgebook = "item.myst.agebook".equals(unlocalizedName);
		if (isLinkbook || isAgebook) {
			NBTTagCompound tag = stack.getTagCompound();

			if (tag != null) {
				Map<String, Object> result = Maps.newHashMap();

				Log.info("%s", tag);
				result.put("type", isLinkbook? "link" : (isAgebook? "age" : "unknown"));
				result.put("destination", tag.getString("agename"));
				result.put("dimension", tag.getInteger("Dimension"));
				addLinkingBookFlags(result, tag);
				addCoordinates(result, tag);

				return result;
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
			Collection<String> tags = tag.getCompoundTag("Flags").func_150296_c();
			for (String s : tags) {
				flags.put(s, Boolean.TRUE);
			}
		}

		map.put("flags", flags);
	}
}
