package openperipheral.integration.cofh.transport;

import cofh.api.transport.IEnderItemHandler;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.integration.cofh.tileentity.SecurityUtils;

public class AdapterEnderItemAttuned implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnderItemHandler.class;
	}

	@Override
	public String getSourceId() {
		return "ender_item";
	}

	@ScriptCallable(description = "Get the channel name of the machine.", returnTypes = ReturnType.STRING)
	public String getChannelName(IEnderItemHandler tileEntity) {
		return tileEntity.getChannelString();
	}

	@ScriptCallable(description = "Get the active frequency of the machine.", returnTypes = ReturnType.NUMBER)
	public int getFrequency(IEnderItemHandler tileEntity) {
		return tileEntity.getFrequency();
	}

	@ScriptCallable(description = "set the active frequency of the machine.", returnTypes = ReturnType.BOOLEAN)
	public boolean setFrequency(IEnderItemHandler tileEntity,
			@Arg(name = "frequency", description = "the frequency you want to set to.") int frequency) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.setFrequency(frequency);
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Clean the active frequency of the machine.")
	public boolean clearFrequency(IEnderItemHandler tileEntity) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.clearFrequency();
	}

	@ScriptCallable(description = "Can the machine output items via the ender net.", returnTypes = ReturnType.BOOLEAN)
	public boolean canSendItems(IEnderItemHandler tileEntity) {
		return tileEntity.canSendItems();
	}

	@ScriptCallable(description = "Can the machine input items via the ender net.", returnTypes = ReturnType.BOOLEAN)
	public boolean canReceiveItems(IEnderItemHandler tileEntity) {
		return tileEntity.canReceiveItems();
	}
}
