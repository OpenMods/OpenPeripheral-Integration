package openperipheral.integration.vanilla;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.Vec3;
import openmods.inventory.IInventoryProvider;
import openperipheral.api.helpers.EntityMetaProviderSimple;

// Well, not vanilla, but always available
public class InventoryProviderMetaProvider extends EntityMetaProviderSimple<IInventoryProvider> {

	@Override
	public String getKey() {
		return "inventory-provider";
	}

	@Override
	public Object getMeta(IInventoryProvider target, Vec3 relativePos) {
		IInventory inventory = target.getInventory();
		return InventoryMetaProvider.wrapToProxyTable(inventory);
	}

}
