package openperipheral.integration.thermalexpansion;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetadataProvider;
import cofh.api.item.ISecureItem;

import com.google.common.collect.Maps;

public class SecureItemMetaProvider implements IItemStackMetadataProvider<ISecureItem> {

	@Override
	public Class<? extends ISecureItem> getTargetClass() {
		return ISecureItem.class;
	}

	@Override
	public String getKey() {
		return "secure-item";
	}

	@Override
	public Object getMeta(ISecureItem target, ItemStack stack) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("owner", target.getOwnerString());
		return map;
	}

}
