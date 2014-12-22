package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterAutoJukebox implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox");

	private final Function0<Boolean> GET_CAN_COPY = MethodAccess.create(boolean.class, CLASS, "getCanCopy");
	private final Function0<Boolean> GET_CAN_PLAY = MethodAccess.create(boolean.class, CLASS, "getCanPlay");

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

	@LuaCallable(description = "Can a disc be played?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getCanPlay(Object tileEntityAutoJukebox) {
		return GET_CAN_PLAY.call(tileEntityAutoJukebox);
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
