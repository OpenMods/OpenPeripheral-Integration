package openperipheral.integration.vanilla;

import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterFluidTank implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IFluidTank.class;
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Returns info containing the capacity of the tank and the FluidStack it holds.")
	public FluidTankInfo getInfo(IFluidTank tank) {
		return tank.getInfo();
	}
}
