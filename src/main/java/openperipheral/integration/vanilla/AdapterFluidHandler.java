package openperipheral.integration.vanilla;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

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
			@Arg(name = "direction", description = "The internal direction of the tank.") EnumFacing direction) {
		return fluidHandler.getTankInfo(direction);
	}

}
