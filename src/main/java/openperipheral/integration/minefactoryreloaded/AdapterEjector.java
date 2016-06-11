package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterEjector implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityEjector");

	private final Function0<Boolean> GET_IS_WHITELIST = MethodAccess.create(boolean.class, CLASS, "getIsWhitelist");
	private final Function1<Void, Boolean> SET_IS_WHITELIST = MethodAccess.create(void.class, CLASS, boolean.class, "setIsWhitelist");

	private final Function0<Boolean> GET_MATCH_META = MethodAccess.create(boolean.class, CLASS, "getIsIDMatch");
	private final Function1<Void, Boolean> SET_MATCH_META = MethodAccess.create(void.class, CLASS, boolean.class, "setIsIDMatch");

	private final Function0<Boolean> GET_MATCH_NBT = MethodAccess.create(boolean.class, CLASS, "getIsNBTMatch");
	private final Function1<Void, Boolean> SET_MATCH_NBT = MethodAccess.create(void.class, CLASS, boolean.class, "setIsNBTMatch");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "mfr_ejector";
	}

	@ScriptCallable(description = "Is whitelist enabled?", returnTypes = ReturnType.BOOLEAN)
	public boolean getIsWhitelist(Object tileEntityEjector) {
		return GET_IS_WHITELIST.call(tileEntityEjector);
	}

	@ScriptCallable(description = "Set the value of whitelist toggle")
	public void setIsWhitelist(Object tileEntityEjector, @Arg(name = "isWhitelist") boolean isWhitelist) {
		SET_IS_WHITELIST.call(tileEntityEjector, isWhitelist);
	}

	@ScriptCallable(description = "Is NBT Match enabled?", returnTypes = ReturnType.BOOLEAN)
	public boolean getMatchNBT(Object tileEntityEjector) {
		return GET_MATCH_NBT.call(tileEntityEjector);
	}

	@ScriptCallable(description = "Set the value of NBT Match toggle")
	public void setMatchNBT(Object tileEntityEjector, @Arg(name = "matchNBT") boolean matchNBT) {
		SET_MATCH_NBT.call(tileEntityEjector, matchNBT);
	}

	@ScriptCallable(description = "Is Match Meta enabled?", returnTypes = ReturnType.BOOLEAN)
	public boolean getMatchMeta(Object tileEntityEjector) {
		// getIsIDMatch returns the boolean value of _ignoreDamage, so if it is true then getIsIDMatch returns false.
		// Odd naming convention handled here to represent GUI button values.
		boolean isIdMatch = GET_MATCH_META.call(tileEntityEjector);
		return !isIdMatch;
	}

	@ScriptCallable(description = "Set the value of Match Damage toggle")
	public void setMatchMeta(Object tileEntityEjector, @Arg(name = "matchMeta") boolean matchMeta) {
		SET_MATCH_META.call(tileEntityEjector, !matchMeta);
	}

}
