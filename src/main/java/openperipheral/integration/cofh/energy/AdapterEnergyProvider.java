package openperipheral.integration.cofh.energy;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;
import cofh.api.energy.IEnergyProvider;

public class AdapterEnergyProvider implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnergyProvider.class;
	}

	@Override
	public String getSourceId() {
		return "rf_provider";
	}

	@ScriptCallable(description = "Get the energy stored in the machine.", returnTypes = ReturnType.NUMBER)
	public int getEnergyStored(IEnergyProvider tileEntity,
			@Optionals @Arg(name = "slot", description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getEnergyStored(side != null? side : ForgeDirection.UNKNOWN);
	}

	@ScriptCallable(description = "Get the max energy stored in the machine.", returnTypes = ReturnType.NUMBER)
	public int getMaxEnergyStored(IEnergyProvider tileEntity,
			@Optionals @Arg(name = "slot", description = "The direction you are interested in. (north, south, east, west, up or down)") ForgeDirection side) {
		return tileEntity.getMaxEnergyStored(side != null? side : ForgeDirection.UNKNOWN);
	}

}
