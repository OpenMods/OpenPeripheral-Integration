package openperipheral.integration.cofh.transport;

import openperipheral.api.*;
import openperipheral.integration.cofh.tileentity.SecurityUtils;
import cofh.api.transport.IEnderFluidHandler;

public class AdapterEnderFluidAttuned implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEnderFluidHandler.class;
	}

	@LuaCallable(description = "Get the channel name of the machine.", returnTypes = LuaReturnType.STRING)
	public String getChannelName(IEnderFluidHandler tileEntity) {
		return tileEntity.getChannelString();
	}

	@LuaCallable(description = "Get the active frequency of the machine.", returnTypes = LuaReturnType.NUMBER)
	public int getFrequency(IEnderFluidHandler tileEntity) {
		return tileEntity.getFrequency();
	}

	@LuaCallable(description = "set the active frequency of the machine.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean setFrequency(IEnderFluidHandler tileEntity,
			@Arg(name = "frequency", description = "the frequency you want to set to.") int frequency) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.setFrequency(frequency);
	}

	@LuaCallable(description = "Clean the active frequency of the machine.")
	public boolean clearFrequency(IEnderFluidHandler tileEntity) {
		SecurityUtils.checkAccess(tileEntity);
		return tileEntity.clearFrequency();
	}

	@LuaCallable(description = "Can the machine output fluids via the ender net.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canSendFluid(IEnderFluidHandler tileEntity) {
		return tileEntity.canSendFluid();
	}

	@LuaCallable(description = "Can the machine input fluids via the ender net.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canReceiveFluid(IEnderFluidHandler tileEntity) {
		return tileEntity.canReceiveFluid();
	}
}
