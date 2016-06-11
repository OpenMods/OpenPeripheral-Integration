package openperipheral.integration.forestry;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

public class IndividualMetaProvider implements IItemStackCustomMetaProvider<Item> {

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "individual";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (AlleleManager.alleleRegistry != null) {
			IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
			if (ind != null) return ConverterIIndividual.describeIndividual(ind);
		}

		return null;
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		if (AlleleManager.alleleRegistry != null) { return AlleleManager.alleleRegistry.isIndividual(stack); }

		return false;
	}
}