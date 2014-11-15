package openperipheral.integration.buildcraft;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

import com.google.common.base.Preconditions;

@SuppressWarnings("deprecation")
public class AdapterPowerReceptor implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IPowerReceptor.class;
	}

	private static PowerReceiver getPowerReceiver(IPowerReceptor powerReceptor, ForgeDirection direction) {
		PowerReceiver powerReceiver = powerReceptor.getPowerReceiver(direction);
		Preconditions.checkNotNull(powerReceiver, "Invalid target");
		return powerReceiver;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getMinMJReceived(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getMinEnergyReceived();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getMaxMJReceived(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getMaxEnergyReceived();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getMaxMJStored(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getMaxEnergyStored();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getActivationMJ(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getActivationEnergy();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getMJStored(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getEnergyStored();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getAverageMJReceived(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getAveragePowerReceived();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getAverageMJUsed(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getAveragePowerUsed();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER)
	public double getAverageMJLost(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getAveragePowerLost();
	}

	@LuaCallable(returnTypes = LuaReturnType.STRING)
	public PowerHandler.Type getPowerReceptorType(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getType();
	}

}
