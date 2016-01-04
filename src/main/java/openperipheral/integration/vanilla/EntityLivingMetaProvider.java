package openperipheral.integration.vanilla;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import openperipheral.api.helpers.EntityMetaProviderSimple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EntityLivingMetaProvider extends EntityMetaProviderSimple<EntityLivingBase> {

	@Override
	public String getKey() {
		return "living";
	}

	@Override
	public Object getMeta(EntityLivingBase target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		{
			Map<String, ItemStack> armor = Maps.newHashMap();
			armor.put("boots", target.getEquipmentInSlot(1));
			armor.put("leggings", target.getEquipmentInSlot(2));
			armor.put("chestplate", target.getEquipmentInSlot(3));
			armor.put("helmet", target.getEquipmentInSlot(4));
			map.put("armor", armor);
		}

		map.put("heldItem", target.getEquipmentInSlot(0));

		{
			Map<Object, String> potionEffects = Maps.newHashMap();
			Collection<PotionEffect> effects = target.getActivePotionEffects();

			int count = 1;
			for (PotionEffect effect : effects) {
				potionEffects.put(count++, effect.getEffectName());
			}
			map.put("potionEffects", potionEffects);
		}

		map.put("health", target.getHealth());
		map.put("maxHealth", target.getMaxHealth());
		map.put("isAirborne", target.isAirBorne);
		map.put("isBurning", target.isBurning());
		map.put("isAlive", target.isEntityAlive());
		map.put("isInWater", target.isInWater());
		map.put("isOnLadder", target.isOnLadder());
		map.put("isSleeping", target.isPlayerSleeping());
		map.put("isRiding", target.isRiding());
		map.put("isSneaking", target.isSneaking());
		map.put("isSprinting", target.isSprinting());
		map.put("isWet", target.isWet());
		map.put("isChild", target.isChild());
		map.put("isDead", target.isDead);
		map.put("yaw", target.rotationYaw);
		map.put("pitch", target.rotationPitch);
		map.put("yawHead", target.rotationYawHead);

		{
			Vec3 posVec = new Vec3(target.posX, target.posY + 1.62F, target.posZ);
			Vec3 lookVec = target.getLook(1.0f);
			Vec3 targetVec = posVec.addVector(lookVec.xCoord * 10f, lookVec.yCoord * 10f, lookVec.zCoord * 10f);

			MovingObjectPosition mop = target.worldObj.rayTraceBlocks(posVec, targetVec);

			if (mop != null) {
				Map<String, Object> hit = Maps.newHashMap();
				if (mop.typeOfHit == MovingObjectType.BLOCK) {
					hit.put("type", "block");
					Map<String, Object> lookingAt = Maps.newHashMap();
					final BlockPos blockPos = mop.getBlockPos();
					if (relativePos != null) {
						lookingAt.put("x", blockPos.getX() - relativePos.xCoord);
						lookingAt.put("y", blockPos.getY() - relativePos.yCoord);
						lookingAt.put("z", blockPos.getZ() - relativePos.zCoord);
					} else {
						lookingAt.put("x", blockPos.getX());
						lookingAt.put("y", blockPos.getX());
						lookingAt.put("z", blockPos.getZ());
					}
					hit.put("position", lookingAt);
				} else if (mop.typeOfHit == MovingObjectType.ENTITY) {
					hit.put("type", "entity");
					hit.put("id", mop.entityHit.getEntityId());
				}

				map.put("lookingAt", hit);
			}
		}

		map.put("potion_effects", getPotionEffects(target));

		return map;
	}

	private static Object getPotionEffects(EntityLivingBase target) {
		final Collection<PotionEffect> effects = target.getActivePotionEffects();

		final List<Map<String, Object>> effectsInfo = Lists.newArrayList();

		for (PotionEffect effect : effects) {
			final Map<String, Object> entry = Maps.newHashMap();

			entry.put("duration", effect.getDuration() / 20); // ticks!
			entry.put("amplifier", effect.getAmplifier());
			entry.put("is_ambient", effect.getIsAmbient());

			entry.put("effect", ItemPotionMetaProvider.getPotionInfo(effect.getPotionID()));

			effectsInfo.add(entry);
		}

		return effectsInfo;
	}

}
