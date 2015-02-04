package openperipheral.integration.ic2;

import ic2.api.tile.IEnergyStorage;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

@Asynchronous
public class AdapterEnergyStorage implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyStorage.class;
	}

	@Override
	public String getSourceId() {
		return "eu_storage";
	}

	@ScriptCallable(description = "Get the EU capacity of this block", returnTypes = ReturnType.NUMBER)
	public int getEUCapacity(IEnergyStorage storage) {
		return storage.getCapacity();
	}

	@ScriptCallable(description = "Get how much EU is stored in this block", returnTypes = ReturnType.NUMBER)
	public int getEUStored(IEnergyStorage storage) {
		return storage.getStored();
	}

	@ScriptCallable(description = "Get the EU output per tick", returnTypes = ReturnType.NUMBER)
	public double getEUOutputPerTick(IEnergyStorage storage) {
		return storage.getOutputEnergyUnitsPerTick();
	}

}
