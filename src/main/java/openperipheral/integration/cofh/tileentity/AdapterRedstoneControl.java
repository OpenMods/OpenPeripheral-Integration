package openperipheral.integration.cofh.tileentity;

import cofh.api.tileentity.IRedstoneControl;
import cofh.api.tileentity.IRedstoneControl.ControlMode;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterRedstoneControl implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IRedstoneControl.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_redstone";
	}

	@ScriptCallable()
	public void setRedstoneControl(IRedstoneControl target,
			@Arg(name = "control") ControlMode control) {
		SecurityUtils.checkAccess(target);
		target.setControl(control);
	}

	@ScriptCallable(returnTypes = ReturnType.STRING)
	public ControlMode getRedstoneControl(IRedstoneControl target) {
		return target.getControl();
	}

}
