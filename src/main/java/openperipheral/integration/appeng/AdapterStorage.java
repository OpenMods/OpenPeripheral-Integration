package openperipheral.integration.appeng;

import appeng.api.implementations.tiles.IChestOrDrive;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.IMultiReturn;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.api.helpers.Index;
import openperipheral.api.helpers.MultiReturn;

public class AdapterStorage implements IPeripheralAdapter {

	private static String intToState(int state) {
		switch (state) {
			case 0:
				return "missing";
			case 1:
				return "green";
			case 2:
				return "orange";
			case 3:
				return "red";
			default:
				return "unknown";
		}
	}

	@Override
	public String getSourceId() {
		return "me_storage";
	}

	@Override
	public Class<?> getTargetClass() {
		return IChestOrDrive.class;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getCellCount(IChestOrDrive target) {
		return target.getCellCount();
	}

	@ScriptCallable(returnTypes = { ReturnType.NUMBER, ReturnType.STRING })
	public IMultiReturn getCellStatus(IChestOrDrive target, @Arg(name = "slot") Index slot) {
		try {
			int status = target.getCellStatus(slot.value);
			return MultiReturn.wrap(status, intToState(status));
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid cell index: " + slot);
		}
	}
}
