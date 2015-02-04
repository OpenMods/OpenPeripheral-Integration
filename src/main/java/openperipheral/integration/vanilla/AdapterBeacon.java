package openperipheral.integration.vanilla;

import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterBeacon implements IPeripheralAdapter {
	private static final String NONE = "None";

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
		Integer effectId = beacon.getPrimaryEffect();
		return getEffectName(effectId);
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Get the secondary effect of the beacon")
	public String getSecondaryEffect(TileEntityBeacon beacon) {
		Integer effectId = beacon.getSecondaryEffect();
		return getEffectName(effectId);
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the height of the beacon's pyramid")
	public int getLevels(TileEntityBeacon beacon) {
		return beacon.getLevels();
	}

	private static String getEffectName(int effectId) {
		if (effectId != 0) {
			PotionEffect effect = new PotionEffect(effectId, 180, 0, true);
			return effect.getEffectName();
		}
		return NONE;
	}
}
