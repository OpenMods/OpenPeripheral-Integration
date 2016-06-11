package openperipheral.integration.ic2;

import com.google.common.collect.Maps;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import java.util.Map;
import net.minecraft.item.ItemStack;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

class ElectricItemMetaProvider extends ItemStackMetaProviderSimple<IElectricItem> {

	@Override
	public String getKey() {
		return "electric";
	}

	@Override
	public Object getMeta(IElectricItem target, ItemStack stack) {
		Map<String, Object> electricInfo = Maps.newHashMap();

		electricInfo.put("tier", target.getTier(stack));
		electricInfo.put("maxCharge", target.getMaxCharge(stack));
		electricInfo.put("transferLimit", target.getTransferLimit(stack));
		electricInfo.put("canProvideEnergy", target.canProvideEnergy(stack));
		electricInfo.put("charge", ElectricItem.manager.getCharge(stack));

		return electricInfo;
	}
}