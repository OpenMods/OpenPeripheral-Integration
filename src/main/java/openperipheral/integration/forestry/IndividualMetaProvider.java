package openperipheral.integration.forestry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;

@SuppressWarnings("serial")
public class IndividualMetaProvider extends ItemStackMetaProviderSimple<Item> {

	@Override
	public String getKey() {
		return "individual";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		return ind != null? ConverterIIndividual.describeIndividual(ind) : null;
	}
}