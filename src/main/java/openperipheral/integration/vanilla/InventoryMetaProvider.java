package openperipheral.integration.vanilla;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.Vec3;
import openmods.utils.InventoryUtils;
import openperipheral.api.helpers.EntityMetaProviderSimple;

@SuppressWarnings("serial")
public class InventoryMetaProvider extends EntityMetaProviderSimple<IInventory> {

	@Override
	public String getKey() {
		return "inventory";
	}

	@Override
	public Object getMeta(IInventory target, Vec3 relativePos) {
		return InventoryUtils.getAllItems(target);
	}

}
