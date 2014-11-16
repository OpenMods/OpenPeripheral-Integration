package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.ReflectionHelper;
import openperipheral.api.*;

public class AdapterAutoJukebox implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoJukebox");

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
		return ReflectionHelper.call(tileEntityAutoJukebox, "getCanCopy");
	}

	@LuaCallable(description = "Set wheather a disc can be copied")
	public void setCanCopy(Object tileEntityAutoJukebox, @Arg(name = "copyable") boolean copyable) {
		ReflectionHelper.call(tileEntityAutoJukebox, "setCanCopy", ReflectionHelper.primitive(copyable));
	}

	@LuaCallable(description = "Can a disc be played?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getCanPlay(Object tileEntityAutoJukebox) {
		return ReflectionHelper.call(tileEntityAutoJukebox, "getCanPlay");
	}

	@LuaCallable(description = "Set wheather a disc can be played")
	public void setCanPlay(Object tileEntityAutoJukebox, @Arg(name = "playable") boolean playable) {
		ReflectionHelper.call(tileEntityAutoJukebox, "setCanPlay", ReflectionHelper.primitive(playable));
	}

	@LuaCallable(description = "Copy record")
	public void copy(Object tileEntityAutoJukebox) {
		ReflectionHelper.call(tileEntityAutoJukebox, "copyRecord");
	}

	@LuaCallable(description = "Play record")
	public void play(Object tileEntityAutoJukebox) {
		ReflectionHelper.call(tileEntityAutoJukebox, "playRecord");
	}

	@LuaCallable(description = "Stop record")
	public void stop(Object tileEntityAutoJukebox) {
		ReflectionHelper.call(tileEntityAutoJukebox, "stopRecord");
	}

}
