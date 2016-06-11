package openperipheral.integration.vanilla;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import openmods.utils.InventoryUtils;
import openperipheral.api.helpers.EntityMetaProviderSimple;
import openperipheral.api.meta.IMetaProviderProxy;
import openperipheral.integration.OpcAccess;

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
		Map<Integer, IMetaProviderProxy> result = Maps.newHashMap();
		for (Map.Entry<Integer, ItemStack> e : InventoryUtils.getAllItems(target).entrySet())
			result.put(e.getKey() + 1, OpcAccess.itemStackMetaBuilder.createProxy(e.getValue()));
		return result;
	}

}
