package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityFurnace;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterFurnace implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityFurnace.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_furnace";
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Number of ticks the current item will cook for")
	public int getBurnTime(TileEntityFurnace furnace) {
		return furnace.furnaceBurnTime;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Number of ticks the current item has been cooking")
	public int getCookTime(TileEntityFurnace furnace) {
		return furnace.furnaceCookTime;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Number of ticks the current item would take to cook")
	public int getCurrentItemBurnTime(TileEntityFurnace furnace) {
		return furnace.currentItemBurnTime;
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Is the furnace currently burning?")
	public boolean isBurning(TileEntityFurnace furnace) {
		return furnace.isBurning();
	}
}
