package openperipheral.integration.vanilla;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import openperipheral.api.helpers.ItemStackMetaProviderSimple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ItemPotionMetaProvider extends ItemStackMetaProviderSimple<ItemPotion> {

	@Override
	public String getKey() {
		return "potion";
	}

	@Override
	public Object getMeta(ItemPotion target, ItemStack stack) {
		final List<PotionEffect> effects = target.getEffects(stack);

		final Map<String, Object> results = Maps.newHashMap();

		results.put("splash", ItemPotion.isSplash(stack.getItemDamage()));

		final List<Map<String, Object>> effectsInfo = Lists.newArrayList();

		for (PotionEffect effect : effects) {
			final Map<String, Object> entry = Maps.newHashMap();

			entry.put("duration", effect.getDuration() / 20); // ticks!
			entry.put("amplifier", effect.getAmplifier());
			entry.put("effect", getPotionInfo(effect.getPotionID()));

			effectsInfo.add(entry);
		}

		results.put("effects", effectsInfo);

		return results;
	}

	public static Map<String, Object> getPotionInfo(int potionId) {
		if (potionId < 0 || potionId >= Potion.potionTypes.length) return null;

		final Map<String, Object> result = Maps.newHashMap();

		final Potion potion = Potion.potionTypes[potionId];
		result.put("name", potion.getName());
		result.put("instant", potion.isInstant());
		result.put("color", potion.getLiquidColor());

		return result;
	}
}
