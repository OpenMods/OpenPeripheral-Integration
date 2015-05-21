package openperipheral.integration.minefactoryreloaded;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.meta.IItemStackMetaProvider;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class SafariNetMetaProvider implements IItemStackMetaProvider<Item> {

	private final Class<?> cls = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.item.ItemSafariNet");

	private final Map<String, String> safariNets = ImmutableMap.of(
			"item.mfr.safarinet.reusable", "reusable",
			"item.mfr.safarinet.singleuse", "single_use",
			"item.mfr.safarinet.jailer", "jailer"
			);

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends Item> getTargetClass() {
		return (Class<? extends Item>)cls;
	}

	@Override
	public String getKey() {
		return "safari_net";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		Map<String, Object> result = Maps.newHashMap();

		String type = safariNets.get(target.getUnlocalizedName());
		result.put("type", Objects.firstNonNull(type, "unknown"));

		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null) {
			if (tag.getBoolean("hide")) {
				result.put("captured", "?");
			} else if (tag.hasKey("id", Constants.NBT.TAG_STRING)) {
				result.put("captured", tag.getString("id"));
			}
		}

		return result;
	}
}
