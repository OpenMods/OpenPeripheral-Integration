package openperipheral.integration.cofh.item;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetadataProvider;
import cofh.api.item.IEmpowerableItem;

public class EmpoweredItemMetaProvider implements IItemStackMetadataProvider<IEmpowerableItem> {

	@Override
	public Class<? extends IEmpowerableItem> getTargetClass() {
		return IEmpowerableItem.class;
	}

	@Override
	public String getKey() {
		return "empowered";
	}

	@Override
	public Object getMeta(IEmpowerableItem target, ItemStack stack) {
		return target.isEmpowered(stack);
	}

}
