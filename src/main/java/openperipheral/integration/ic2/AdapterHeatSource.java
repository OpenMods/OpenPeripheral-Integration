package openperipheral.integration.ic2;

import ic2.api.energy.tile.IHeatSource;
import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterHeatSource implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IHeatSource.class;
	}

	@Override
	public String getSourceId() {
		return "ic2_heat_source";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getMaxKineticEnergy(IHeatSource target, @Arg(name = "side") ForgeDirection side) {
		return target.maxrequestHeatTick(side);
	}

}
