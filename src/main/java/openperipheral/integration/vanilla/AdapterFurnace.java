package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityFurnace;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterFurnace implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityFurnace.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_furnace";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Number of ticks the current item will cook for")
	public int getBurnTime(TileEntityFurnace furnace) {
		return furnace.furnaceBurnTime;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Number of ticks the current item has been cooking")
	public int getCookTime(TileEntityFurnace furnace) {
		return furnace.furnaceCookTime;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Number of ticks the current item would take to cook")
	public int getCurrentItemBurnTime(TileEntityFurnace furnace) {
		return furnace.currentItemBurnTime;
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Is the furnace currently burning?")
	public boolean isBurning(TileEntityFurnace furnace) {
		return furnace.isBurning();
	}
}
