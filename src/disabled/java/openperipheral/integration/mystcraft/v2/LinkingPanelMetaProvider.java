package openperipheral.integration.mystcraft.v2;

import java.util.Collection;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

import com.google.common.collect.Sets;
import com.xcompwiz.mystcraft.api.hook.PageAPI;

public class LinkingPanelMetaProvider implements IItemStackCustomMetaProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		final PageAPI pageApi = MystcraftAccess.pageApi;
		if (pageApi == null) return false;
		return pageApi.hasLinkPanel(stack);
	}

	@Override
	public String getKey() {
		return "link_panel";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (MystcraftAccess.pageApi == null) return null;

		Set<String> result = Sets.newHashSet();

		final Collection<String> props = MystcraftAccess.pageApi.getPageLinkProperties(stack);

		if (props != null) result.addAll(props);

		return result;
	}
}
