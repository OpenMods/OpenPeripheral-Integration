package openperipheral.integration.railcraft;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterTileSteamTurbine implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.blocks.machine.alpha.TileSteamTurbine");

	private final Function0<IInventory> GET_INVENTORY = MethodAccess.create(IInventory.class, CLASS, "getInventory");
	private final Function0<Float> GET_OUTPUT = MethodAccess.create(float.class, CLASS, "getOutput");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_turbine";
	}

	@ScriptCallable(description = "Get the undamagedness of the rotor, from 0% (dead) to 100% (brand new)", returnTypes = ReturnType.NUMBER)
	public Integer getTurbineRotorStatus(Object tileSteamTurbine) {
		IInventory inventory = GET_INVENTORY.call(tileSteamTurbine);

		if (inventory != null && inventory.getSizeInventory() >= 1) {
			ItemStack itemStack = inventory.getStackInSlot(0);
			if (itemStack != null) {
				Item item = itemStack.getItem();
				if (item != null) { return 100 - (int)(itemStack.getItemDamage() * 100.0 / item.getMaxDamage()); }
			}
		}
		return null;
	}

	@ScriptCallable(description = "Get power output percentage", returnTypes = ReturnType.NUMBER)
	public float getTurbineOutput(Object tileSteamTurbine) {
		return GET_OUTPUT.call(tileSteamTurbine);
	}
}
