package openperipheral.integration.cofh.transport;

import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.integration.cofh.tileentity.SecurityUtils;
import cofh.api.transport.IEnderEnergyHandler;

public class AdapterEnderEnergyAttuned implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnderEnergyHandler.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_ender_energy";
	}

	@ScriptCallable(description = "Get the channel name of the machine.", returnTypes = ReturnType.STRING)
	public String getChannelName(IEnderEnergyHandler tileEntity) {
		return tileEntity.getChannelString();
	}

	@ScriptCallable(description = "Get the active frequency of the machine.", returnTypes = ReturnType.NUMBER)
	public int getFrequency(IEnderEnergyHandler tileEntity) {
		return tileEntity.getFrequency();
	}

	@ScriptCallable(description = "set the active frequency of the machine.", returnTypes = ReturnType.BOOLEAN)
	public boolean setFrequency(IEnderEnergyHandler tileEntity,
			@Arg(name = "frequency", description = "the frequency you want to set to.") int frequency) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.setFrequency(frequency);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Clean the active frequency of the machine.")
	public boolean clearFrequency(IEnderEnergyHandler tileEntity) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.clearFrequency();
	}

	@ScriptCallable(description = "Can the machine output energy via the ender net.", returnTypes = ReturnType.BOOLEAN)
	public boolean canSendEnergy(IEnderEnergyHandler tileEntity) {
		return tileEntity.canSendEnergy();
	}

	@ScriptCallable(description = "Can the machine input energy via the ender net.", returnTypes = ReturnType.BOOLEAN)
	public boolean canReceiveEnergy(IEnderEnergyHandler tileEntity) {
		return tileEntity.canReceiveEnergy();
	}
}
