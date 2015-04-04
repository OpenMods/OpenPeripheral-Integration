package openperipheral.integration.mystcraft.v2;

import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.meta.IItemStackMetaProvider;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xcompwiz.mystcraft.api.MystAPI;
import com.xcompwiz.mystcraft.api.linking.ILinkInfo;

public class BookMetaProvider implements IItemStackMetaProvider<Item> {

	private static Class<?> CLS = ReflectionHelper.getClass("com.xcompwiz.mystcraft.item.ItemLinking");

	private final MystAPI api;

	private static final Set<String> FLAGS = ImmutableSet.of(
			"Relative",
			"Maintain Momentum",
			"Intra Linking",
			"Generate Platform",
			"Disarm",
			"Following"
			);

	public BookMetaProvider(MystAPI api) {
		this.api = api;
	}

	@Override
	public String getKey() {
		return "myst_book";
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends Item> getTargetClass() {
		return (Class<? extends Item>)CLS;
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		final NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) return null;

		final ILinkInfo linkInfo = api.getLinkingAPI().createLinkInfo(stack.stackTagCompound);

		final String unlocalizedName = target.getUnlocalizedName();
		final boolean isLinkbook = "item.myst.linkbook".equals(unlocalizedName);
		final boolean isAgebook = "item.myst.agebook".equals(unlocalizedName);

		Map<String, Object> result = Maps.newHashMap();

		result.put("type", isLinkbook? "link" : (isAgebook? "age" : "unknown"));
		result.put("destination", linkInfo.getDisplayName());
		result.put("dimension", linkInfo.getDimensionUID());

		{
			Map<String, Boolean> flags = Maps.newHashMap();
			for (String flag : FLAGS)
				flags.put(flag, linkInfo.getFlag(flag));
			result.put("flags", flags);
		}

		{
			ChunkCoordinates coords = linkInfo.getSpawn();
			if (coords != null) result.put("spawn", Lists.newArrayList(coords.posX, coords.posY, coords.posZ));
		}

		result.put("spawnYaw", linkInfo.getSpawnYaw());

		return result;

	}
}
