package openperipheral.integration.cofh.tileentity;

import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import cofh.api.tileentity.IEnergyInfo;

public class AdapterEnergyInfo implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyInfo.class;
	}

	@Override
	public String getSourceId() {
		return "rf_info";
	}

	@ScriptCallable(description = "Gets the EnergyPerTick of the machine.", returnTypes = ReturnType.NUMBER)
	public int getEnergyPerTickInfo(IEnergyInfo tileEntity) {
		return tileEntity.getInfoEnergyPerTick();
	}

	@ScriptCallable(description = "Gets the max EnergyPerTick of the machine.", returnTypes = ReturnType.NUMBER)
	public int getMaxEnergyPerTickInfo(IEnergyInfo tileEntity) {
		return tileEntity.getInfoMaxEnergyPerTick();
	}

	@ScriptCallable(description = "Gets the stored Energy of the machine.", returnTypes = ReturnType.NUMBER)
	public int getEnergyInfo(IEnergyInfo tileEntity) {
		return tileEntity.getInfoEnergyStored();
	}

	@ScriptCallable(description = "Gets the max Energy of the machine.", returnTypes = ReturnType.NUMBER)
	public int getMaxEnergyInfo(IEnergyInfo tileEntity) {
		return tileEntity.getInfoMaxEnergyStored();
	}
}
