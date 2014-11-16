package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterChronotyper implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityChronotyper");

	@Override
	public String getSourceId() {
		return "mfr_chronotyper";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@LuaCallable(description = "Should adults be moved?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getMoveAdults(Object tileEntityChronotyper) {
		return ReflectionHelper.call(tileEntityChronotyper, "getMoveOld");
	}

	@LuaCallable(description = "Set wheather adults should be moved")
	public void setMoveAdults(Object tileEntityChronotyper, @Arg(name = "adults") boolean adults) {
		ReflectionHelper.call(tileEntityChronotyper, "setMoveOld", ReflectionHelper.primitive(adults));
	}

}
