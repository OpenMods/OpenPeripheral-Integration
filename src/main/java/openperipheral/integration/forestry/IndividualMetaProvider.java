package openperipheral.integration.forestry;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetaProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;

public class IndividualMetaProvider implements IItemStackMetaProvider<Object> {
	@Override
	public Class<?> getTargetClass() {
		return Object.class;
	}

	@Override
	public String getKey() {
		return "individual";
	}

	@Override
	public Object getMeta(Object target, ItemStack stack) {
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		return ind != null? ConverterIIndividual.describeIndividual(ind) : null;
	}
}