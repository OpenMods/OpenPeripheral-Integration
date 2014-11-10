package openperipheral.integration.cofh.tileentity;

import openperipheral.api.*;
import cofh.api.tileentity.IReconfigurableSides;

public class AdapterReconfigurableSides implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IReconfigurableSides.class;
	}

	@LuaCallable(returnTypes = LuaType.BOOLEAN, description = "Decrement the config for a given side. Returns true if config was changed")
	public boolean decrSide(IReconfigurableSides target,
			@Arg(name = "side", type = LuaType.NUMBER) int side) {
		SecurityUtils.checkAccess(target);
		return target.decrSide(side);
	}

	@LuaCallable(returnTypes = LuaType.BOOLEAN, description = "Increment the config for a given side. Returns true if config was changed")
	public boolean incrSide(IReconfigurableSides target,
			@Arg(name = "side", type = LuaType.NUMBER) int side) {
		SecurityUtils.checkAccess(target);
		return target.incrSide(side);
	}

	@LuaCallable(returnTypes = LuaType.BOOLEAN, description = "Set the config for a given side. Returns true if config was changed")
	public boolean setSide(IReconfigurableSides target,
			@Arg(name = "side", type = LuaType.NUMBER) int side,
			@Arg(name = "config", type = LuaType.NUMBER) int config) {
		SecurityUtils.checkAccess(target);
		return target.setSide(side, config);
	}

	@LuaCallable(returnTypes = LuaType.BOOLEAN, description = "Reset configs on all sides to their base values. Returns true if reset was successful")
	public boolean resetSides(IReconfigurableSides target) {
		SecurityUtils.checkAccess(target);
		return target.resetSides();
	}

	@LuaCallable(returnTypes = LuaType.NUMBER, description = "Returns the number of possible config settings for a given side")
	public int getNumConfig(IReconfigurableSides target,
			@Arg(name = "side", type = LuaType.NUMBER) int side) {
		return target.getNumConfig(side);
	}
}
