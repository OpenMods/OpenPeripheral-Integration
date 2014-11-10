package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityComparator;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterComparator implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityComparator.class;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Get the strength of the output signal")
	public int getOutputSignal(TileEntityComparator comparator) {
		return comparator.getOutputSignal();
	}

}
