package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterEjector implements IPeripheralAdapter {

	private static final Class<?> EJECTOR_CLASS = ReflectionHelper.getClass(
			"powercrystals.minefactoryreloaded.tile.machine.TileEntityEjector"
			);

	@Override
	public Class<?> getTargetClass() {
		return EJECTOR_CLASS;
	}

	@Override
	public String getSourceId() {
		return "mfr_";
	}

	@LuaCallable(description = "Is whitelist enabled?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getIsWhitelist(Object tileEntityEjector) {
		return ReflectionHelper.call(tileEntityEjector, "getIsWhitelist");
	}

	@LuaCallable(description = "Set the value of whitelist toggle")
	public void setIsWhitelist(Object tileEntityEjector, @Arg(name = "isWhitelist") boolean isWhitelist) {
		ReflectionHelper.call(tileEntityEjector, "setIsWhitelist", ReflectionHelper.primitive(isWhitelist));
	}

	@LuaCallable(description = "Is NBT Match enabled?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getMatchNBT(Object tileEntityEjector) {
		return ReflectionHelper.call(tileEntityEjector, "getIsNBTMatch");
	}

	@LuaCallable(description = "Set the value of NBT Match toggle")
	public void setMatchNBT(Object tileEntityEjector, @Arg(name = "matchNBT") boolean matchNBT) {
		ReflectionHelper.call(tileEntityEjector, "setIsNBTMatch", ReflectionHelper.primitive(matchNBT));
	}

	@LuaCallable(description = "Is Match Meta enabled?", returnTypes = LuaReturnType.BOOLEAN)
	public boolean getMatchMeta(Object tileEntityEjector) {
		// getIsIDMatch returns the boolean value of _ignoreDamage, so if it is true then getIsIDMatch returns false.
		// Odd naming convention handled here to represent GUI button values.
		boolean isIdMatch = ReflectionHelper.call(tileEntityEjector, "getIsIDMatch");
		return !isIdMatch;
	}

	@LuaCallable(description = "Set the value of Match Damage toggle")
	public void setMatchMeta(Object tileEntityEjector, @Arg(name = "matchMeta") boolean matchMeta) {
		ReflectionHelper.call(tileEntityEjector, "setIsIDMatch", ReflectionHelper.primitive(!matchMeta));
	}

}
