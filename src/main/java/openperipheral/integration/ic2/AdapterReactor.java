package openperipheral.integration.ic2;

import ic2.api.reactor.IReactor;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

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

	@ScriptCallable(description = "Get the heat of the reactor", returnTypes = ReturnType.NUMBER)
	public int getHeat(IReactor reactor) {
		return reactor.getHeat();
	}

	@ScriptCallable(description = "Get the maximum heat of the reactor before it explodes", returnTypes = ReturnType.NUMBER)
	public int getMaxHeat(IReactor reactor) {
		return reactor.getMaxHeat();
	}

	@ScriptCallable(description = "Get the EU output of this reactor", returnTypes = ReturnType.NUMBER)
	public float getEUOutput(IReactor reactor) {
		return reactor.getReactorEnergyOutput();
	}

	@ScriptCallable(description = "Returns true if the reactor is active", returnTypes = ReturnType.BOOLEAN)
	public boolean isActive(IReactor reactor) {
		return reactor.produceEnergy();
	}
}
