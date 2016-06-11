package openperipheral.integration.cofh.tileentity;

import cofh.api.tileentity.ISecurable;
import cofh.api.tileentity.ISecurable.AccessMode;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
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

	@ScriptCallable(description = "Is this username allowed to access the machine.", returnTypes = ReturnType.BOOLEAN)
	public boolean canPlayerAccess(ISecurable tile,
			@Arg(name = "username", description = "The username to check for") String name) {
		return tile.canPlayerAccess(name);
	}

	@ScriptCallable(description = "Gets the AccessMode of this machine.", returnTypes = ReturnType.STRING)
	public AccessMode getAccess(ISecurable tile) {
		return tile.getAccess();
	}

}
