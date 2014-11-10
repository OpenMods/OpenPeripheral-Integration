package openperipheral.integration.cofh.transport;

import openperipheral.api.*;
import openperipheral.integration.cofh.tileentity.SecurityUtils;
import cofh.api.transport.IEnderEnergyHandler;

public class AdapterEnderEnergyAttuned implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnderEnergyHandler.class;
	}

	@LuaCallable(description = "Get the channel name of the machine.", returnTypes = LuaReturnType.STRING)
	public String getChannelName(IEnderEnergyHandler tileEntity) {
		return tileEntity.getChannelString();
	}

	@LuaCallable(description = "Get the active frequency of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getFrequency(IEnderEnergyHandler tileEntity) {
		return tileEntity.getFrequency();
	}

	@LuaCallable(description = "set the active frequency of the machine.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean setFrequency(IEnderEnergyHandler tileEntity,
			@Arg(name = "frequency", description = "the frequency you want to set to.") int frequency) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.setFrequency(frequency);
	}

	@LuaCallable(description = "Clean the active frequency of the machine.")
	public boolean clearFrequency(IEnderEnergyHandler tileEntity) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.clearFrequency();
	}

	@LuaCallable(description = "Can the machine output energy via the ender net.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canSendEnergy(IEnderEnergyHandler tileEntity) {
		return tileEntity.canSendEnergy();
	}

	@LuaCallable(description = "Can the machine input energy via the ender net.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canReceiveEnergy(IEnderEnergyHandler tileEntity) {
		return tileEntity.canReceiveEnergy();
	}
}
