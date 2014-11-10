package openperipheral.integration.cofh.tileentity;

import openperipheral.api.*;
import cofh.api.tileentity.ISecurable;
import cofh.api.tileentity.ISecurable.AccessMode;

public class AdapterSecureTile implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return ISecurable.class;
	}

	@LuaCallable(description = "Gets the owner of the machine.", returnTypes = LuaReturnType.STRING)
	public String getOwnerName(ISecurable tile) {
		return tile.getOwnerName();
	}

	@LuaCallable(description = "Is this username allowed to access the machine.", returnTypes = LuaReturnType.BOOLEAN)
	public boolean canPlayerAccess(ISecurable tile,
			@Arg(name = "username", description = "The username to check for") String name) {
		return tile.canPlayerAccess(name);
	}

	@LuaCallable(description = "Gets the AccessMode of this machine.", returnTypes = LuaReturnType.STRING)
	public AccessMode getAccess(ISecurable tile) {
		return tile.getAccess();
	}

}
