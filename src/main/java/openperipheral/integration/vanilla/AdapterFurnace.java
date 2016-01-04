package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityFurnace;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

@Asynchronous
public class AdapterFurnace implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityFurnace.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_furnace";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Number of ticks the furnace is burning")
	public int getCurrentBurnTime(TileEntityFurnace furnace) {
		return furnace.getField(0);
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Total number furnace will burn on consumed fuel item")
	public int getTotalBurnTime(TileEntityFurnace furnace) {
		return furnace.getField(1);
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Number of ticks the current item has been cooking")
	public int getCurrentCookTime(TileEntityFurnace furnace) {
		return furnace.getField(2);
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Total number of ticks the current has to cook")
	public int getTotalCookTime(TileEntityFurnace furnace) {
		return furnace.getField(3);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Is the furnace currently burning?")
	public boolean isBurning(TileEntityFurnace furnace) {
		return furnace.isBurning();
	}
}
