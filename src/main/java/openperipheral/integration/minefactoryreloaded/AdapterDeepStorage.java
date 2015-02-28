package openperipheral.integration.minefactoryreloaded;

import net.minecraft.item.ItemStack;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;

public class AdapterDeepStorage implements IPeripheralAdapter {

	@Override
	public String getSourceId() {
		return "mfr-deep-storage";
	}

	@Override
	public Class<?> getTargetClass() {
		return IDeepStorageUnit.class;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE)
	public ItemStack getStoredItems(IDeepStorageUnit target) {
		return target.getStoredItemType();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getMaxStoredItems(IDeepStorageUnit target) {
		return target.getMaxStoredCount();
	}
}
