package openperipheral.integration.buildcraft;

import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;
import buildcraft.api.tiles.IHasWork;

public class AdapterMachine implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IHasWork.class;
	}

	@LuaCallable(description = "Checks if the machine is running.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean hasWork(IHasWork action) {
		return action.hasWork();
	}
}
