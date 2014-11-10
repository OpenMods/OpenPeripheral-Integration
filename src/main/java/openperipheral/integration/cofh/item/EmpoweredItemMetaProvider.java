package openperipheral.integration.cofh.item;

import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import cofh.api.item.IEmpowerableItem;

@SuppressWarnings("serial")
public class EmpoweredItemMetaProvider extends ItemStackMetaProviderSimple<IEmpowerableItem> {

	@Override
	public String getKey() {
		return "empowered";
	}

	@Override
	public Object getMeta(IEmpowerableItem target, ItemStack stack) {
		return target.isEmpowered(stack);
	}

}
