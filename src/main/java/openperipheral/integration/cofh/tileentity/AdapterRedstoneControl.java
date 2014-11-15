package openperipheral.integration.cofh.tileentity;

import openperipheral.api.*;
import cofh.api.tileentity.IRedstoneControl;
import cofh.api.tileentity.IRedstoneControl.ControlMode;

public class AdapterRedstoneControl implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IRedstoneControl.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_redstone";
	}

	@LuaCallable()
	public void setRedstoneControl(IRedstoneControl target,
			@Arg(name = "control") ControlMode control) {
		SecurityUtils.checkAccess(target);
		target.setControl(control);
	}

	@LuaCallable(returnTypes = LuaReturnType.STRING)
	public ControlMode getRedstoneControl(IRedstoneControl target) {
		return target.getControl();
	}

}
