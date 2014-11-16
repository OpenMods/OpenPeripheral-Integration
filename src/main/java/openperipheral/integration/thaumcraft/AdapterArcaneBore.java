/**
 *
 */
package openperipheral.integration.thaumcraft;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import openmods.reflection.*;
import openmods.reflection.MethodAccess.Function0;
import openperipheral.api.*;

/**
 * @author Katrina
 *
 */
@Asynchronous
public class AdapterArcaneBore implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileArcaneBore");
	private final Class<?> ITEM_ELEMENTAL_PICK = ReflectionHelper.getClass("thaumcraft.common.items.equipment.ItemElementalPickaxe");

	private final FieldAccess<Boolean> HAS_PICKAXE = FieldAccess.create(CLASS, "hasPickaxe");
	private final FieldAccess<Boolean> HAS_FOCUS = FieldAccess.create(CLASS, "hasFocus");
	private final FieldAccess<Integer> AREA = FieldAccess.create(CLASS, "area");
	private final FieldAccess<Integer> SPEED = FieldAccess.create(CLASS, "speed");
	private final FieldAccess<Integer> MAX_RADIUS = FieldAccess.create(CLASS, "maxRadius");

	private final Function0<Boolean> GETTING_POWER = MethodAccess.create(boolean.class, CLASS, "gettingPower");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraf_bore";
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Does the arcane bore have a pickaxe.")
	public boolean hasPickaxe(Object target) {
		return HAS_PICKAXE.get(target);
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Does the arcane bore have a focus.")
	public boolean hasFocus(Object target) {
		return HAS_FOCUS.get(target);
	}

	public ItemStack getPick(Object bore) {
		return (bore instanceof IInventory)? ((IInventory)bore).getStackInSlot(1) : null;
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Is the pick broken?")
	public boolean isPickaxeBroken(Object target) {
		ItemStack pick = getPick(target);
		return pick != null && pick.getItemDamage() + 1 == pick.getMaxDamage();
	}

	@Asynchronous(false)
	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Is the Arcane bore active?")
	public boolean isWorking(Object target) {
		ItemStack pick = getPick(target);
		Boolean hasPower = GETTING_POWER.call(target);
		return hasPower && hasFocus(target) && hasPickaxe(target) && pick.isItemStackDamageable() && !isPickaxeBroken(target);
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the radius of the bore's effects")
	public int getRadius(Object target) {
		return 1 + (AREA.get(target) + MAX_RADIUS.get(target)) * 2;
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the speed of the bore")
	public int getSpeed(Object target) {
		return SPEED.get(target);
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Does the bore mine native clusters as well as normal ores")
	public boolean hasNativeClusters(Object target) {
		ItemStack pick = getPick(target);
		return pick != null && ITEM_ELEMENTAL_PICK.isInstance(pick.getItem());
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the fortune level the Bore is mining with")
	public int getFortune(Object target) {
		ItemStack pick = getPick(target);
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, pick);
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Does the Bore mine with silk touch")
	public boolean hasSilkTouch(Object target) {
		ItemStack pick = getPick(target);
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, pick) > 0;
	}
}
