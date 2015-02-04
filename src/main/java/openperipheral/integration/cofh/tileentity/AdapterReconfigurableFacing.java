package openperipheral.integration.cofh.tileentity;

import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import cofh.api.tileentity.IReconfigurableFacing;

public class AdapterReconfigurableFacing implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IReconfigurableFacing.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_facing";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Returns the current facing of the block")
	public int getFacing(IReconfigurableFacing target) {
		return target.getFacing();
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Returns whether or not the block's face can be aligned with the Y Axis.")
	public boolean allowYAxisFacing(IReconfigurableFacing target) {
		return target.allowYAxisFacing();
	}

	/**
	 *
	 *
	 * @, false otherwise.
	 */

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Attempt to rotate the block. Arbitrary based on implementation. Returns true if rotation was successful")
	public boolean rotateBlock(IReconfigurableFacing target) {
		SecurityUtils.checkAccess(target);
		return target.rotateBlock();
	}

	/**
	 *
	 *
	 * @param side
	 *            The side to set the facing to.
	 * @return True if the facing was set, false otherwise.
	 */
	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Set the facing of the block. Returns true if rotation was successful")
	public boolean setFacing(IReconfigurableFacing target,
			@Arg(name = "side") int side) {
		SecurityUtils.checkAccess(target);
		return target.setFacing(side);
	}
}
