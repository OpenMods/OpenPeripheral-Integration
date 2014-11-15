package openperipheral.integration.ic2;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import openperipheral.api.*;

import com.google.common.base.Preconditions;

@Asynchronous
public class AdapterReactorChamber implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IReactorChamber.class;
	}

	@Override
	public String getSourceId() {
		return "ic2_chamber";
	}

	private static IReactor getReactor(IReactorChamber chamber) {
		IReactor reactor = chamber.getReactor();
		Preconditions.checkNotNull(reactor, "No reactor");
		return reactor;
	}

	@LuaCallable(description = "Check if reactor is in valid state", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isValid(IReactorChamber chamber) {
		return chamber.getReactor() != null;
	}

	@LuaCallable(description = "Get the heat of the reactor", returnTypes = LuaReturnType.NUMBER)
	public int getHeat(IReactorChamber chamber) {
		return getReactor(chamber).getHeat();
	}

	@LuaCallable(description = "Get the maximum heat of the reactor before it explodes", returnTypes = LuaReturnType.NUMBER)
	public int getMaxHeat(IReactorChamber chamber) {
		return getReactor(chamber).getMaxHeat();
	}

	@LuaCallable(description = "Get the EU output of this reactor", returnTypes = LuaReturnType.NUMBER)
	public float getEUOutput(IReactorChamber chamber) {
		return getReactor(chamber).getReactorEnergyOutput();
	}

	@LuaCallable(description = "Returns true if the reactor is active", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isActive(IReactorChamber chamber) {
		return getReactor(chamber).produceEnergy();
	}
}
