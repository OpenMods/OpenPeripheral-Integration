package openperipheral.integration.vanilla;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import openperipheral.api.*;

public class AdapterFluidHandler implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IFluidHandler.class;
	}

	@Override
	public String getSourceId() {
		return "fluid_handler";
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "A table of tanks will be returned, each with a table of information")
	public FluidTankInfo[] getTankInfo(IFluidHandler fluidHandler,
			@Arg(name = "direction", description = "The internal direction of the tank. If you're not sure, use 'unknown' (north, south, east, west, up, down or unknown)") ForgeDirection direction) {
		return fluidHandler.getTankInfo(direction);
	}

}
