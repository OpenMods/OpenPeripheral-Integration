package openperipheral.integration.buildcraft;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.*;
import buildcraft.api.transport.*;
import buildcraft.api.transport.IPipeTile.PipeType;

import com.google.common.base.Preconditions;

public class AdapterPipe implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IPipeTile.class;
	}

	private static IPipe getPipe(IPipeTile target) {
		IPipe pipe = target.getPipe();
		Preconditions.checkNotNull(pipe, "Invalid pipe");
		return pipe;
	}

	@LuaCallable(description = "Checks if this pipe has a gate", returnTypes = LuaReturnType.BOOLEAN)
	public boolean hasGate(IPipeTile target, @Arg(name = "side") ForgeDirection side) {
		return getPipe(target).hasGate(side);
	}

	@LuaCallable(description = "Checks if a wire is on the pipe", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isWired(IPipeTile target,
			@Arg(name = "wire", description = "The colour of the wire") PipeWire wire) {
		return getPipe(target).isWired(wire);
	}

	@LuaCallable(description = "Checks if a wire on the pipe is active", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isWireActive(IPipeTile target,
			@Arg(name = "wire", description = "The colour of the wire") PipeWire wire) {
		return getPipe(target).isWireActive(wire);
	}

	@LuaCallable(description = "Get type of pipe", returnTypes = LuaReturnType.STRING)
	public PipeType getPipeType(IPipeTile target) {
		return target.getPipeType();
	}

	@LuaCallable(description = "Get type of pipe", returnTypes = LuaReturnType.BOOLEAN)
	public boolean isPipeConnected(IPipeTile target, @Arg(name = "side") ForgeDirection side) {
		return target.isPipeConnected(side);
	}
}
