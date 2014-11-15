package openperipheral.integration.thaumcraft;

import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

@SuppressWarnings("serial")
public class EssentiaContainerMetaProvider extends ItemStackMetaProviderSimple<IEssentiaContainerItem> {

	@Override
	public String getKey() {
		return "essentia_container";
	}

	@Override
	public AspectList getMeta(IEssentiaContainerItem target, ItemStack stack) {
		return target.getAspects(stack);
	}

}
