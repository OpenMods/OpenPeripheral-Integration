package openperipheral.integration.appeng;

import openmods.Log;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import openperipheral.integration.OpenPeripheralIntegration;

import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAETagCompound;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NBTHashMetaProvider extends ItemStackMetaProviderSimple<Item> {
	private static final String SUFFIX_NBTHASH = "-NBTHash";

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt.hasKey(OpenPeripheralIntegration.MOD_ID + SUFFIX_NBTHASH)) { return nbt.getString(OpenPeripheralIntegration.MOD_ID + SUFFIX_NBTHASH); }
		}

		return null;
	}

	@Override
	public String getKey() {
		return "nbt_id";
	}

	// To get a unique hash id from the nbt tag we are:
	// 1. Compressing the nbt tag to a byte-array using Minecrafts
	// CompressedStreamTools
	// 2. Creating a MD5 digest over the compressed data
	// 3. And returning it as a hex-encoded String
	public static String getNBTHash(IAETagCompound tag) {
		String result = "00000000000000000000000000000000";
		try {
			byte[] compressed = CompressedStreamTools.compress(tag.getNBTTagCompoundCopy());
			byte[] digest = MessageDigest.getInstance("MD5").digest(compressed);
			result = new String(Hex.encodeHex(digest));
		} catch (IOException e) {
			Log.warn("Could not compress NBT Tag using CompressedStreamTools. Stack comparison with NBT data will not work!");
		} catch (NoSuchAlgorithmException e) {
			Log.warn("MD5 digest algorithm does not exist. Stack comparison with NBT data will not work!");
		}
		return result;
	}

	// Stacks created by this are only made for consumption by computercraft
	// computers. They contain modified nbt data and can not be trusted in other
	// situations.
	public static ItemStack convertStacks(IAEItemStack aeStack) {
		// First ask AE2 to give us the vanilla ItemStack.
		// We're losing information by doing this and need to readd it later on.
		ItemStack stack = aeStack.getItemStack().copy();

		// We want to use OpenPeripherals ItemStack converter so we get all the
		// extra data that is provided. But this is a one way conversion, i.e.
		// we will not be able to request specific items from the ME Network if
		// we don't have something to identify NBT tags without too much hassle.
		// To get the converter to include this data on the "toLua" step we are
		// adding the hash to the nbt tag now and let the NBTHashMetaProvider
		// include the value in the actual return table.
		if (aeStack.hasTagCompound()) {
			stack.getTagCompound().setString(OpenPeripheralIntegration.MOD_ID + SUFFIX_NBTHASH, NBTHashMetaProvider.getNBTHash(aeStack.getTagCompound()));
		}

		// We are also adding additional data stored in IAEItemStack to the item
		// stacks nbt data.
		// This is later on being processing by the AEItemStackMetaProvider so
		// it is included in the Object lua is receiving.
		if (aeStack.isCraftable()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
			}
			nbt.setBoolean(OpenPeripheralIntegration.MOD_ID + AEItemStackMetaProvider.IS_CRAFTABLE_SUFFIX, true);
			stack.setTagCompound(nbt);
		}

		return stack;
	}
}
