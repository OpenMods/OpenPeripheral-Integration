package openperipheral.integration.vanilla;

import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import openmods.utils.InventoryUtils;
import openperipheral.api.ApiAccess;
import openperipheral.api.helpers.EntityMetaProviderSimple;
import openperipheral.api.meta.IItemStackPartialMetaBuilder;
import openperipheral.api.meta.IMetaProviderProxy;

import com.google.common.collect.Maps;

public class InventoryMetaProvider extends EntityMetaProviderSimple<IInventory> {

	@Override
	public String getKey() {
		return "inventory";
	}

	@Override
	public Object getMeta(IInventory target, Vec3 relativePos) {
		return wrapToProxyTable(target);
	}

	public static Map<Integer, IMetaProviderProxy> wrapToProxyTable(IInventory target) {
		final IItemStackPartialMetaBuilder builder = ApiAccess.getApi(IItemStackPartialMetaBuilder.class);

		Map<Integer, IMetaProviderProxy> result = Maps.newHashMap();
		for (Map.Entry<Integer, ItemStack> e : InventoryUtils.getAllItems(target).entrySet())
			result.put(e.getKey() + 1, builder.createProxy(e.getValue()));
		return result;
	}

}
