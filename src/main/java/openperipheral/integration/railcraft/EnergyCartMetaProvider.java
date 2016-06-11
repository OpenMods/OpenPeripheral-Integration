package openperipheral.integration.railcraft;

import com.google.common.collect.Maps;
import java.util.Map;
import mods.railcraft.api.carts.IEnergyTransfer;
import net.minecraft.util.Vec3;
import openperipheral.api.helpers.EntityMetaProviderSimple;

public class EnergyCartMetaProvider extends EntityMetaProviderSimple<IEnergyTransfer> {

	@Override
	public String getKey() {
		return "energy_cart";
	}

	@Override
	public Object getMeta(IEnergyTransfer target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("maxPower", target.getCapacity());
		map.put("currentPower", target.getEnergy());
		map.put("powerTier", target.getTier());
		map.put("transferRate", target.getTransferLimit());
		return map;
	}

}
