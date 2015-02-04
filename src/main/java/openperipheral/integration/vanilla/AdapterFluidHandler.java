package openperipheral.integration.vanilla;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.*;

public class AdapterFluidHandler implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IFluidHandler.class;
	}

	@Override
	public String getSourceId() {
		return "fluid_handler";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "A table of tanks will be returned, each with a table of information")
	public FluidTankInfo[] getTankInfo(IFluidHandler fluidHandler,
			@Optionals @Arg(name = "direction", description = "The internal direction of the tank. If you're not sure, use 'unknown' (north, south, east, west, up, down or unknown)") ForgeDirection direction) {
		return fluidHandler.getTankInfo(direction != null? direction : ForgeDirection.UNKNOWN);
	}

}
