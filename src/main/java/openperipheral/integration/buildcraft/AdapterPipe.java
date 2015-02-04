package openperipheral.integration.buildcraft;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import buildcraft.api.transport.*;
import buildcraft.api.transport.IPipeTile.PipeType;

import com.google.common.base.Preconditions;

public class AdapterPipe implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IPipeTile.class;
	}

	@Override
	public String getSourceId() {
		return "buildcraft_pipe";
	}

	private static IPipe getPipe(IPipeTile target) {
		IPipe pipe = target.getPipe();
		Preconditions.checkNotNull(pipe, "Invalid pipe");
		return pipe;
	}

	@ScriptCallable(description = "Checks if this pipe has a gate", returnTypes = ReturnType.BOOLEAN)
	public boolean hasGate(IPipeTile target, @Arg(name = "side") ForgeDirection side) {
		return getPipe(target).hasGate(side);
	}

	@ScriptCallable(description = "Checks if a wire is on the pipe", returnTypes = ReturnType.BOOLEAN)
	public boolean isWired(IPipeTile target,
			@Arg(name = "wire", description = "The colour of the wire") PipeWire wire) {
		return getPipe(target).isWired(wire);
	}

	@ScriptCallable(description = "Checks if a wire on the pipe is active", returnTypes = ReturnType.BOOLEAN)
	public boolean isWireActive(IPipeTile target,
			@Arg(name = "wire", description = "The colour of the wire") PipeWire wire) {
		return getPipe(target).isWireActive(wire);
	}

	@ScriptCallable(description = "Get type of pipe", returnTypes = ReturnType.STRING)
	public PipeType getPipeType(IPipeTile target) {
		return target.getPipeType();
	}

	@ScriptCallable(description = "Get type of pipe", returnTypes = ReturnType.BOOLEAN)
	public boolean isPipeConnected(IPipeTile target, @Arg(name = "side") ForgeDirection side) {
		return target.isPipeConnected(side);
	}
}
