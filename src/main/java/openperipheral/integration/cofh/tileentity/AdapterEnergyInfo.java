package openperipheral.integration.cofh.tileentity;

import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;
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

	@LuaCallable(description = "Gets the EnergyPerTick of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getEnergyPerTick(IEnergyInfo tileEntity) {
		return tileEntity.getInfoEnergyPerTick();
	}

	@LuaCallable(description = "Gets the max EnergyPerTick of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getMaxEnergyPerTick(IEnergyInfo tileEntity) {
		return tileEntity.getInfoMaxEnergyPerTick();
	}

	@LuaCallable(description = "Gets the stored Energy of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getEnergy(IEnergyInfo tileEntity) {
		return tileEntity.getInfoEnergyStored();
	}

	@LuaCallable(description = "Gets the max Energy of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getMaxEnergy(IEnergyInfo tileEntity) {
		return tileEntity.getInfoMaxEnergyStored();
	}
}
