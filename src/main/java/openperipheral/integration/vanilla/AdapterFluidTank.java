package openperipheral.integration.vanilla;

import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterFluidTank implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IFluidTank.class;
	}

	@Override
	public String getSourceId() {
		return "fluid_tank";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Returns info containing the capacity of the tank and the FluidStack it holds.")
	public FluidTankInfo getInfo(IFluidTank tank) {
		return tank.getInfo();
	}
}
