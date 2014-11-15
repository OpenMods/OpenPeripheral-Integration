package openperipheral.integration.ic2;

import ic2.api.reactor.IReactor;
import openperipheral.api.*;

@Asynchronous
public class AdapterReactor implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IReactor.class;
	}

	@Override
	public String getSourceId() {
		return "ic2_reactor";
	}

	@LuaCallable(description = "Get the heat of the reactor", returnTypes = LuaReturnType.NUMBER)
	public int getHeat(IReactor reactor) {
		return reactor.getHeat();
	}

	@LuaCallable(description = "Get the maximum heat of the reactor before it explodes", returnTypes = LuaReturnType.NUMBER)
	public int getMaxHeat(IReactor reactor) {
		return reactor.getMaxHeat();
	}

	@LuaCallable(description = "Get the EU output of this reactor", returnTypes = LuaReturnType.NUMBER)
	public float getEUOutput(IReactor reactor) {
		return reactor.getReactorEnergyOutput();
	}

	@LuaCallable(description = "Returns true if the reactor is active", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isActive(IReactor reactor) {
		return reactor.produceEnergy();
	}
}
