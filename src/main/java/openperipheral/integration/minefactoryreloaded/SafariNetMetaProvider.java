package openperipheral.integration.minefactoryreloaded;

import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.IItemStackMetadataProvider;

import com.google.common.collect.ImmutableSet;

public class SafariNetMetaProvider implements IItemStackMetadataProvider<Item> {

	private final Set<String> safariNets = ImmutableSet.of(
			"item.mfr.safarinet.reusable",
			"item.mfr.safarinet.singleuse",
			"item.mfr.safarinet.jailer"
			);

	@Override
	public Class<Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "captured";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (safariNets.contains(target.getUnlocalizedName())) {
			NBTTagCompound tag = stack.getTagCompound();
			if (tag != null && tag.hasKey("id")) return tag.getString("id");
		}

		return null;
	}

}
