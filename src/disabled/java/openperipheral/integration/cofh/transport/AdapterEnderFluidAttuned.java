package openperipheral.integration.cofh.transport;

import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.integration.cofh.tileentity.SecurityUtils;
import cofh.api.transport.IEnderFluidHandler;

public class AdapterEnderFluidAttuned implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnderFluidHandler.class;
	}

	@ScriptCallable(description = "Get the channel name of the machine.", returnTypes = ReturnType.STRING)
	public String getChannelName(IEnderFluidHandler tileEntity) {
		return tileEntity.getChannelString();
	}

	@Override
	public String getSourceId() {
		return "cofh_ender_fluid";
	}

	@ScriptCallable(description = "Get the active frequency of the machine.", returnTypes = ReturnType.NUMBER)
	public int getFrequency(IEnderFluidHandler tileEntity) {
		return tileEntity.getFrequency();
	}

	@ScriptCallable(description = "set the active frequency of the machine.", returnTypes = ReturnType.BOOLEAN)
	public boolean setFrequency(IEnderFluidHandler tileEntity,
			@Arg(name = "frequency", description = "the frequency you want to set to.") int frequency) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.setFrequency(frequency);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Clean the active frequency of the machine.")
	public boolean clearFrequency(IEnderFluidHandler tileEntity) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.clearFrequency();
	}

	@ScriptCallable(description = "Can the machine output fluids via the ender net.", returnTypes = ReturnType.BOOLEAN)
	public boolean canSendFluid(IEnderFluidHandler tileEntity) {
		return tileEntity.canSendFluid();
	}

	@ScriptCallable(description = "Can the machine input fluids via the ender net.", returnTypes = ReturnType.BOOLEAN)
	public boolean canReceiveFluid(IEnderFluidHandler tileEntity) {
		return tileEntity.canReceiveFluid();
	}
}
