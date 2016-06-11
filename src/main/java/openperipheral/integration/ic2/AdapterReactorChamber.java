package openperipheral.integration.ic2;

import com.google.common.base.Preconditions;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

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

	@ScriptCallable(description = "Check if reactor is in valid state", returnTypes = ReturnType.BOOLEAN)
	public boolean isValid(IReactorChamber chamber) {
		return chamber.getReactor() != null;
	}

	@ScriptCallable(description = "Get the heat of the reactor", returnTypes = ReturnType.NUMBER)
	public int getHeat(IReactorChamber chamber) {
		return getReactor(chamber).getHeat();
	}

	@ScriptCallable(description = "Get the maximum heat of the reactor before it explodes", returnTypes = ReturnType.NUMBER)
	public int getMaxHeat(IReactorChamber chamber) {
		return getReactor(chamber).getMaxHeat();
	}

	@ScriptCallable(description = "Get the EU output of this reactor", returnTypes = ReturnType.NUMBER)
	public float getEUOutput(IReactorChamber chamber) {
		return getReactor(chamber).getReactorEnergyOutput();
	}

	@ScriptCallable(description = "Returns true if the reactor is active", returnTypes = ReturnType.BOOLEAN)
	public boolean isActive(IReactorChamber chamber) {
		return getReactor(chamber).produceEnergy();
	}
}
