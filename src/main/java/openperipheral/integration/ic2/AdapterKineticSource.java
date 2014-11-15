package openperipheral.integration.ic2;

import ic2.api.energy.tile.IKineticSource;
import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;

public class AdapterKineticSource implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IKineticSource.class;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public int getMaxKineticEnergy(IKineticSource target, @Arg(name = "side") ForgeDirection side) {
		return target.maxrequestkineticenergyTick(side);
	}

}
