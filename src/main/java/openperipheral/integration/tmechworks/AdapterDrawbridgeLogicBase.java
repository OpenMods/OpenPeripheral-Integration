package openperipheral.integration.tmechworks;

import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import tmechworks.lib.blocks.IDrawbridgeLogicBase;

public class AdapterDrawbridgeLogicBase implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IDrawbridgeLogicBase.class;
	}

	@Override
	public String getSourceId() {
		return "tmechworks_drawbridge";
	}

	@Asynchronous
	@ScriptCallable(description = "Checks if the drawbridge is extended or not", returnTypes = ReturnType.BOOLEAN)
	public boolean hasExtended(IDrawbridgeLogicBase drawbridge) {
		return drawbridge.hasExtended();
	}
}
