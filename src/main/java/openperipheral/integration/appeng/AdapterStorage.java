package openperipheral.integration.appeng;

import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;
import openperipheral.api.helpers.MultiReturn;
import appeng.api.implementations.tiles.IChestOrDrive;

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
	public IMultiReturn getCellStatus(IChestOrDrive target, @Arg(name = "slot") int slot) {
		try {
			int status = target.getCellStatus(slot - 1);
			return MultiReturn.wrap(status, intToState(status));
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid cell index: " + slot);
		}
	}
}
