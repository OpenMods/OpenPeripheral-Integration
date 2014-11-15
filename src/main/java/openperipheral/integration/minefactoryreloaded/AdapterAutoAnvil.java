package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterAutoAnvil implements IPeripheralAdapter {

	private static final Class<?> AUTOANVIL_CLASS = ReflectionHelper.getClass(
			"powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoAnvil"
			);

	@Override
	public String getSourceId() {
		return "mfr_anvil";
	}

	@Override
	public Class<?> getTargetClass() {
		return AUTOANVIL_CLASS;
	}

	@LuaCallable(description = "Get value of repair only toggle", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getRepairOnly(Object tileEntityAutoAnvil) {
		return ReflectionHelper.call(tileEntityAutoAnvil, "getRepairOnly");
	}

	@LuaCallable(description = "Set the value of repair only toggle")
	public void setRepairOnly(Object tileEntityAutoAnvil, @Arg(name = "repair") boolean repair) {
		// NOTE: This doesn't seem to always work as expected. Consulting Skyboy
		// about it.
		ReflectionHelper.call(tileEntityAutoAnvil, "setRepairOnly", ReflectionHelper.primitive(repair));
	}

}
