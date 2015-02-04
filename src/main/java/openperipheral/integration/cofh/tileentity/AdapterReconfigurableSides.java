package openperipheral.integration.cofh.tileentity;

import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import cofh.api.tileentity.IReconfigurableSides;

public class AdapterReconfigurableSides implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IReconfigurableSides.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_sides";
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Decrement the config for a given side. Returns true if config was changed")
	public boolean decrSide(IReconfigurableSides target,
			@Arg(name = "side") int side) {
		SecurityUtils.checkAccess(target);
		return target.decrSide(side);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Increment the config for a given side. Returns true if config was changed")
	public boolean incrSide(IReconfigurableSides target,
			@Arg(name = "side") int side) {
		SecurityUtils.checkAccess(target);
		return target.incrSide(side);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Set the config for a given side. Returns true if config was changed")
	public boolean setSide(IReconfigurableSides target,
			@Arg(name = "side") int side,
			@Arg(name = "config") int config) {
		SecurityUtils.checkAccess(target);
		return target.setSide(side, config);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Reset configs on all sides to their base values. Returns true if reset was successful")
	public boolean resetSides(IReconfigurableSides target) {
		SecurityUtils.checkAccess(target);
		return target.resetSides();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Returns the number of possible config settings for a given side")
	public int getNumConfig(IReconfigurableSides target,
			@Arg(name = "side") int side) {
		return target.getNumConfig(side);
	}
}
