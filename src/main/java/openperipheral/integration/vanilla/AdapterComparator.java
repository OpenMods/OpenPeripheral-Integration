package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityComparator;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterComparator implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityComparator.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_comparator";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the strength of the output signal")
	public int getOutputSignal(TileEntityComparator comparator) {
		return comparator.getOutputSignal();
	}

}
