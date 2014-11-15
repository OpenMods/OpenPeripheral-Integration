package openperipheral.integration.ic2;

import ic2.api.tile.IEnergyStorage;
import openperipheral.api.*;

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

	@LuaCallable(description = "Get the EU capacity of this block", returnTypes = LuaReturnType.NUMBER)
	public int getEUCapacity(IEnergyStorage storage) {
		return storage.getCapacity();
	}

	@LuaCallable(description = "Get how much EU is stored in this block", returnTypes = LuaReturnType.NUMBER)
	public int getEUStored(IEnergyStorage storage) {
		return storage.getStored();
	}

	@LuaCallable(description = "Get the EU output per tick", returnTypes = LuaReturnType.NUMBER)
	public double getEUOutputPerTick(IEnergyStorage storage) {
		return storage.getOutputEnergyUnitsPerTick();
	}

}
