package openperipheral.integration.cofh.transport;

import openperipheral.api.*;
import openperipheral.integration.cofh.tileentity.SecurityUtils;
import cofh.api.transport.IEnderItemHandler;

public class AdapterEnderItemAttuned implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnderItemHandler.class;
	}

	@Override
	public String getSourceId() {
		return "ender_item";
	}

	@LuaCallable(description = "Get the channel name of the machine.", returnTypes = LuaReturnType.STRING)
	public String getChannelName(IEnderItemHandler tileEntity) {
		return tileEntity.getChannelString();
	}

	@LuaCallable(description = "Get the active frequency of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getFrequency(IEnderItemHandler tileEntity) {
		return tileEntity.getFrequency();
	}

	@LuaCallable(description = "set the active frequency of the machine.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean setFrequency(IEnderItemHandler tileEntity,
			@Arg(name = "frequency", description = "the frequency you want to set to.") int frequency) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.setFrequency(frequency);
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Clean the active frequency of the machine.")
	public boolean clearFrequency(IEnderItemHandler tileEntity) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.clearFrequency();
	}

	@LuaCallable(description = "Can the machine output items via the ender net.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canSendItems(IEnderItemHandler tileEntity) {
		return tileEntity.canSendItems();
	}

	@LuaCallable(description = "Can the machine input items via the ender net.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canReceiveItems(IEnderItemHandler tileEntity) {
		return tileEntity.canReceiveItems();
	}
}
