package openperipheral.integration.mystcraft.v2;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xcompwiz.mystcraft.api.hook.LinkPropertyAPI;
import com.xcompwiz.mystcraft.api.item.IItemPortalActivator;
import com.xcompwiz.mystcraft.api.linking.ILinkInfo;

public class BookMetaProvider extends ItemStackMetaProviderSimple<IItemPortalActivator> implements IItemStackCustomMetaProvider<IItemPortalActivator> {

	@Override
	public String getKey() {
		return "myst_book";
	}

	private static ILinkInfo getLinkInfo(ItemStack stack) {
		if (MystcraftAccess.linkingApi == null) return null;
		final NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) return null;

		return MystcraftAccess.linkingApi.createLinkInfo(tag);
	}

	@Override
	public boolean canApply(IItemPortalActivator target, ItemStack stack) {
		return getLinkInfo(stack) != null;
	}

	@Override
	public Object getMeta(IItemPortalActivator target, ItemStack stack) {
		final ILinkInfo linkInfo = getLinkInfo(stack);
		if (linkInfo == null) return null;

		final String unlocalizedName = stack.getUnlocalizedName();
		final boolean isLinkbook = "item.myst.linkbook".equals(unlocalizedName);
		final boolean isAgebook = "item.myst.agebook".equals(unlocalizedName);

		Map<String, Object> result = Maps.newHashMap();

		result.put("type", isLinkbook? "link" : (isAgebook? "age" : "unknown"));
		result.put("destination", linkInfo.getDisplayName());
		result.put("dimension", linkInfo.getDimensionUID());

		final LinkPropertyAPI linkPropertiesApi = MystcraftAccess.linkPropertiesApi;
		if (linkPropertiesApi != null) {
			final Collection<String> allProperties = linkPropertiesApi.getLinkProperties();

			Set<String> flags = Sets.newHashSet();
			for (String flag : allProperties)
				if (linkInfo.getFlag(flag)) flags.add(flag);

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
