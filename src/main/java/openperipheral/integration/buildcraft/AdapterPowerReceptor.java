package openperipheral.integration.buildcraft;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
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

	@Override
	public String getSourceId() {
		return "mj_receptor";
	}

	private static PowerReceiver getPowerReceiver(IPowerReceptor powerReceptor, ForgeDirection direction) {
		PowerReceiver powerReceiver = powerReceptor.getPowerReceiver(direction);
		Preconditions.checkNotNull(powerReceiver, "Invalid target");
		return powerReceiver;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getMinMJReceived(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getMinEnergyReceived();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getMaxMJReceived(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getMaxEnergyReceived();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getMaxMJStored(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getMaxEnergyStored();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getActivationMJ(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getActivationEnergy();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getMJStored(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getEnergyStored();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getAverageMJReceived(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getAveragePowerReceived();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getAverageMJUsed(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getAveragePowerUsed();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public double getAverageMJLost(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getAveragePowerLost();
	}

	@ScriptCallable(returnTypes = ReturnType.STRING)
	public PowerHandler.Type getPowerReceptorType(IPowerReceptor powerReceptor, @Arg(name = "side") ForgeDirection side) {
		PowerReceiver powerReceiver = getPowerReceiver(powerReceptor, side);
		return powerReceiver.getType();
	}

}
