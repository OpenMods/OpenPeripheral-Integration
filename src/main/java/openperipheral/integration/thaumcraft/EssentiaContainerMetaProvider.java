package openperipheral.integration.thaumcraft;

import net.minecraft.item.ItemStack;
import openperipheral.api.IItemStackMetadataProvider;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

public class EssentiaContainerMetaProvider implements IItemStackMetadataProvider<IEssentiaContainerItem> {

	@Override
	public Class<? extends IEssentiaContainerItem> getTargetClass() {
		return IEssentiaContainerItem.class;
	}

	@Override
	public String getKey() {
		return "essentia_container";
	}

	@Override
	public Object getMeta(IEssentiaContainerItem target, ItemStack stack) {
		AspectList aspects = target.getAspects(stack);
		return ModuleThaumcraft.aspectsToMap(aspects);
	}

}
