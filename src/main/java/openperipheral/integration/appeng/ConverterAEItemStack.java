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

	@Override
	public Object toLua(ITypeConvertersRegistry registry, Object obj) {
		if (obj instanceof IAEItemStack) {
			IAEItemStack aeStack = (IAEItemStack)obj;

			Map<String, Object> result = (Map<String, Object>)registry.toLua(aeStack.getItemStack());
			result.put("is_craftable", aeStack.isCraftable());
			result.put("is_fluid", aeStack.isFluid());
			result.put("is_item", aeStack.isItem());

			return registry.toLua(result);

		}
		return null;
	}

}
