package openperipheral.integration.appeng;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import openperipheral.api.ITypeConverter;
import openperipheral.api.ITypeConvertersRegistry;
import openperipheral.integration.vanilla.ItemFingerprint;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAETagCompound;

import com.google.common.collect.Maps;

public class ConverterAEItemStack implements ITypeConverter {

	@Override
	public Object fromLua(ITypeConvertersRegistry registry, Object obj, Class<?> expected) {
		return null;
	}

	public static NBTTagCompound getTag(IAEItemStack stack) {
		final IAETagCompound tag = stack.getTagCompound();
		return tag != null? tag.getNBTTagCompoundCopy() : null;
	}

	@Override
	public Object toLua(ITypeConvertersRegistry registry, Object obj) {
		if (obj instanceof IAEItemStack) {
			IAEItemStack aeStack = (IAEItemStack)obj;
			ItemFingerprint fingerprint = new ItemFingerprint(aeStack.getItem(), aeStack.getItemDamage(), getTag(aeStack));

			Map<String, Object> result = Maps.newHashMap();
			result.put("fingerprint", fingerprint);
			result.put("size", aeStack.getStackSize());
			result.put("is_craftable", aeStack.isCraftable());
			result.put("is_fluid", aeStack.isFluid());
			result.put("is_item", aeStack.isItem());

			return registry.toLua(result);

		}
		return null;
	}

}
