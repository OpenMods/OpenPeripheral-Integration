package openperipheral.integration.cofh.energy;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;
import cofh.api.energy.IEnergyReceiver;

public class AdapterEnergyReceiver implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyReceiver.class;
	}

	@LuaCallable(description = "Get the energy stored in the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getEnergyStored(IEnergyReceiver tileEntity,
			@Arg(name = "slot", description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getEnergyStored(side);
	}

	@LuaCallable(description = "Get the max energy stored in the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getMaxEnergyStored(IEnergyReceiver tileEntity,
			@Arg(name = "slot", description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getMaxEnergyStored(side);
	}

}
