package openperipheral.integration.cofh.tileentity;

import cofh.api.tileentity.IAugmentable;
import net.minecraft.item.ItemStack;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterAugumentable implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IAugmentable.class;
	}

	@Override
	public String getSourceId() {
		return "augumentable";
	}

	@ScriptCallable(description = "Returns an array of the Augment slots.", returnTypes = ReturnType.TABLE)
	public ItemStack[] getAugumentSlots(IAugmentable tile) {
		return tile.getAugmentSlots();
	}

	@ScriptCallable(description = "Returns a status array for the installed Augmentations.", returnTypes = ReturnType.TABLE)
	public boolean[] getAccess(IAugmentable tile) {
		return tile.getAugmentStatus();
	}
}
