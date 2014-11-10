package openperipheral.integration.cofh.energy;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;
import cofh.api.energy.IEnergyProvider;

public class AdapterEnergyProvider implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyProvider.class;
	}

	@LuaCallable(description = "Get the energy stored in the machine.", returnTypes = LuaType.NUMBER)
	public int getEnergyStored(IEnergyProvider tileEntity,
			@Arg(name = "slot", type = LuaType.STRING, description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getEnergyStored(side);
	}

	@LuaCallable(description = "Get the max energy stored in the machine.", returnTypes = LuaType.NUMBER)
	public int getMaxEnergyStored(IEnergyProvider tileEntity,
			@Arg(name = "slot", type = LuaType.STRING, description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getMaxEnergyStored(side);
	}

}
