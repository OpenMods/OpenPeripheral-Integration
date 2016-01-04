package openperipheral.integration.vanilla;

import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterBeacon implements IPeripheralAdapter {
	@Override
	public Class<?> getTargetClass() {
		return TileEntityBeacon.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_beacon";
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Get the primary effect of the beacon")
	public String getPrimaryEffect(TileEntityBeacon beacon) {
		int effectId = beacon.getField(1);
		return getEffectName(effectId);
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Get the secondary effect of the beacon")
	public String getSecondaryEffect(TileEntityBeacon beacon) {
		Integer effectId = beacon.getField(2);
		return getEffectName(effectId);
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the height of the beacon's pyramid")
	public int getLevels(TileEntityBeacon beacon) {
		return beacon.getField(0);
	}

	private static String getEffectName(int effectId) {
		try {
			return Potion.potionTypes[effectId].getName();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}
