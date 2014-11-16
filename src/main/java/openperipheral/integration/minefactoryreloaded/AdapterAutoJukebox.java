package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openperipheral.api.*;

public class AdapterAutoJukebox implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox");

	private final Function0<Boolean> GET_CAN_COPY = MethodAccess.create(boolean.class, CLASS, "getCanCopy");
	private final Function1<Void, Boolean> SET_CAN_COPY = MethodAccess.create(void.class, CLASS, boolean.class, "setCanCopy");

	private final Function0<Boolean> GET_CAN_PLAY = MethodAccess.create(boolean.class, CLASS, "getCanPlay");
	private final Function1<Void, Boolean> SET_CAN_PLAY = MethodAccess.create(void.class, CLASS, boolean.class, "setCanPlay");

	private final Function0<Void> COPY = MethodAccess.create(void.class, CLASS, "copyRecord");
	private final Function0<Void> PLAY = MethodAccess.create(void.class, CLASS, "playRecord");
	private final Function0<Void> STOP = MethodAccess.create(void.class, CLASS, "stopRecord");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "mfr_jukebox";
	}

	@LuaCallable(description = "Can a disc be copied?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getCanCopy(Object tileEntityAutoJukebox) {
		return GET_CAN_COPY.call(tileEntityAutoJukebox);
	}

	@LuaCallable(description = "Set wheather a disc can be copied")
	public void setCanCopy(Object tileEntityAutoJukebox, @Arg(name = "copyable") boolean copyable) {
		SET_CAN_COPY.call(tileEntityAutoJukebox, copyable);
	}

	@LuaCallable(description = "Can a disc be played?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getCanPlay(Object tileEntityAutoJukebox) {
		return GET_CAN_PLAY.call(tileEntityAutoJukebox);
	}

	@LuaCallable(description = "Set wheather a disc can be played")
	public void setCanPlay(Object tileEntityAutoJukebox, @Arg(name = "playable") boolean playable) {
		SET_CAN_PLAY.call(tileEntityAutoJukebox, playable);
	}

	@LuaCallable(description = "Copy record")
	public void copy(Object tileEntityAutoJukebox) {
		COPY.call(tileEntityAutoJukebox);
	}

	@LuaCallable(description = "Play record")
	public void play(Object tileEntityAutoJukebox) {
		PLAY.call(tileEntityAutoJukebox);
	}

	@LuaCallable(description = "Stop record")
	public void stop(Object tileEntityAutoJukebox) {
		STOP.call(tileEntityAutoJukebox);
	}
}
