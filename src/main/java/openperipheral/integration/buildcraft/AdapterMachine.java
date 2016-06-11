package openperipheral.integration.buildcraft;

import buildcraft.api.tiles.IHasWork;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterMachine implements IPeripheralAdapter {

	@Override
	public String getSourceId() {
		return "buildcraft_machine";
	}

	@Override
	public Class<?> getTargetClass() {
		return IHasWork.class;
	}

	@ScriptCallable(description = "Checks if the machine is running.", returnTypes = ReturnType.BOOLEAN)
	public boolean hasWork(IHasWork action) {
		return action.hasWork();
	}
}
