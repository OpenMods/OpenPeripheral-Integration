package openperipheral.integration.cofh.tileentity;

import cofh.api.tileentity.ISecurable;
import cofh.api.tileentity.ISecurable.AccessMode;
import com.mojang.authlib.GameProfile;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterSecureTile implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return ISecurable.class;
	}

	@Override
	public String getSourceId() {
		return "cofh_secure";
	}

	@ScriptCallable(description = "Gets the owner of the machine.", returnTypes = ReturnType.STRING)
	public String getOwnerName(ISecurable tile) {
		return tile.getOwnerName();
	}

	@ScriptCallable(description = "Gets the owner of the machine.", returnTypes = ReturnType.STRING)
	public GameProfile getOwner(ISecurable tile) {
		return tile.getOwner();
	}

	@ScriptCallable(description = "Gets the AccessMode of this machine.", returnTypes = ReturnType.STRING)
	public AccessMode getAccess(ISecurable tile) {
		return tile.getAccess();
	}

}
