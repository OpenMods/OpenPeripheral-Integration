package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterAutoDisenchanter implements IPeripheralAdapter {

	private static final Class<?> AUTODISENCHANTER_CLASS = ReflectionHelper.getClass(
			"powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoDisenchanter"
			);

	@Override
	public String getSourceId() {
		return "mfr_disenchanter";
	}

	@Override
	public Class<?> getTargetClass() {
		return AUTODISENCHANTER_CLASS;
	}

	@LuaCallable(description = "Get value of repeat disenchant toggle", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getRepeat(Object tileEntityAutoDisenchanter) {
		return ReflectionHelper.call(tileEntityAutoDisenchanter, "getRepeatDisenchant");
	}

	@LuaCallable(description = "Set the value of repeat disenchant toggle")
	public void setRepeat(Object tileEntityAutoDisenchanter, @Arg(name = "repeat") boolean repeat) {
		ReflectionHelper.call(tileEntityAutoDisenchanter, "setRepeatDisenchant", ReflectionHelper.primitive(repeat));
	}

}
