package openperipheral.integration.tmechworks;

import openperipheral.api.*;
import tmechworks.lib.blocks.IDrawbridgeLogicBase;

@Synchronizable
public class AdapterDrawbridgeLogicBase implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IDrawbridgeLogicBase.class;
	}

	@Override
	public String getSourceId() {
		return "tmechworks_drawbridge";
	}

	@LuaCallable(description = "Checks if the drawbridge is extended or not", returnTypes = LuaReturnType.BOOLEAN)
	public boolean hasExtended(IDrawbridgeLogicBase drawbridge) {
		return drawbridge.hasExtended();
	}
}
