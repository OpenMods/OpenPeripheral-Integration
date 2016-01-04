package openperipheral.integration.cofh.energy;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;
import cofh.api.energy.IEnergyReceiver;

public class AdapterEnergyReceiver implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyReceiver.class;
	}

	@Override
	public String getSourceId() {
		return "rf_receiver";
	}

	@ScriptCallable(description = "Get the energy stored in the machine.", returnTypes = ReturnType.NUMBER)
	public int getEnergyStored(IEnergyReceiver tileEntity,
			@Optionals @Arg(name = "side", description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getEnergyStored(side != null? side : ForgeDirection.UNKNOWN);
	}

	@ScriptCallable(description = "Get the max energy stored in the machine.", returnTypes = ReturnType.NUMBER)
	public int getMaxEnergyStored(IEnergyReceiver tileEntity,
			@Optionals @Arg(name = "side", description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getMaxEnergyStored(side != null? side : ForgeDirection.UNKNOWN);
	}

}
