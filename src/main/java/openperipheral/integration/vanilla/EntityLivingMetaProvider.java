package openperipheral.integration.vanilla;

import java.util.Collection;
import java.util.Map;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import openperipheral.api.IEntityMetadataProvider;

import com.google.common.collect.Maps;

public class EntityLivingMetaProvider implements IEntityMetadataProvider<EntityLiving> {

	@Override
	public Class<? extends EntityLiving> getTargetClass() {
		return EntityLiving.class;
	}

	@Override
	public String getKey() {
		return "living";
	}

	@Override
	public Object getMeta(EntityLiving target, Vec3 relativePos) {
		Map<String, Object> map = Maps.newHashMap();

		{
			Map<String, ItemStack> armor = Maps.newHashMap();
			armor.put("boots", target.getCurrentItemOrArmor(1));
			armor.put("leggings", target.getCurrentItemOrArmor(2));
			armor.put("chestplate", target.getCurrentItemOrArmor(3));
			armor.put("helmet", target.getCurrentItemOrArmor(4));
			map.put("armor", armor);
		}

		map.put("heldItem", target.getHeldItem());

		{
			Map<Object, String> potionEffects = Maps.newHashMap();
			@SuppressWarnings("unchecked")
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
			Vec3 posVec = Vec3.createVectorHelper(target.posX, target.posY + 1.62F, target.posZ);
			Vec3 lookVec = target.getLook(1.0f);
			Vec3 targetVec = posVec.addVector(lookVec.xCoord * 10f, lookVec.yCoord * 10f, lookVec.zCoord * 10f);

			MovingObjectPosition mop = target.worldObj.clip(posVec, targetVec);

			if (mop != null) {
				Map<String, Object> hit = Maps.newHashMap();
				if (mop.typeOfHit == EnumMovingObjectType.TILE) {
					hit.put("type", "block");
					Map<String, Object> lookingAt = Maps.newHashMap();
					if (relativePos != null) {
						lookingAt.put("x", mop.blockX - relativePos.xCoord);
						lookingAt.put("y", mop.blockY - relativePos.yCoord);
						lookingAt.put("z", mop.blockZ - relativePos.zCoord);
					} else {
						lookingAt.put("x", mop.blockX);
						lookingAt.put("y", mop.blockY);
						lookingAt.put("z", mop.blockZ);
					}
					hit.put("position", lookingAt);
				} else if (mop.typeOfHit == EnumMovingObjectType.ENTITY) {
					hit.put("type", "entity");
					hit.put("id", mop.entityHit.entityId);
				}

				map.put("lookingAt", hit);
			}
		}

		return map;
	}

}
