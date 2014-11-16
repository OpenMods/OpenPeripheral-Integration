package openperipheral.integration.railcraft;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openmods.utils.ReflectionHelper;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

public class AdapterTileSteamTurbine implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.blocks.machine.alpha.TileSteamTurbine");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_turbine";
	}

	@LuaCallable(description = "Get the undamagedness of the rotor, from 0% (dead) to 100% (brand new)", returnTypes = LuaReturnType.NUMBER)
	public Integer getTurbineRotorStatus(Object tileSteamTurbine) {
		IInventory inventory = ReflectionHelper.call(tileSteamTurbine, "getInventory");

		if (inventory != null && inventory.getSizeInventory() >= 1) {
			ItemStack itemStack = inventory.getStackInSlot(0);
			if (itemStack != null) {
				Item item = itemStack.getItem();
				if (item != null) { return 100 - (int)(itemStack.getItemDamage() * 100.0 / item.getMaxDamage()); }
			}
		}
		return null;
	}

	@LuaCallable(description = "Get power output percentage", returnTypes = LuaReturnType.NUMBER)
	public float getTurbineOutput(Object tileSteamTurbine) {
		return ReflectionHelper.call(tileSteamTurbine, "getOutput");
	}
}
