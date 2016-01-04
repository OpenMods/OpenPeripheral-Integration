package openperipheral.integration.ic2;

import ic2.api.energy.tile.IKineticSource;
import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterKineticSource implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IKineticSource.class;
	}

	@Override
	public String getSourceId() {
		return "ic2_kinetic_source";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getMaxKineticEnergy(IKineticSource target, @Arg(name = "side") ForgeDirection side) {
		return target.maxrequestkineticenergyTick(side);
	}

}
