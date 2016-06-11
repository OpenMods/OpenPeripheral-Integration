/**
 *
 */
package openperipheral.integration.thaumcraft;

import net.minecraft.inventory.IInventory;
import openmods.reflection.FieldAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import thaumcraft.api.aspects.Aspect;

/**
 * @author Katrina
 *
 */
@Asynchronous
public class AdapterDeconstructor implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileDeconstructionTable");

	private final FieldAccess<Aspect> ASPECT = FieldAccess.create(CLASS, "aspect");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_deconstructor";
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Does the Table have an aspect in it")
	public boolean hasAspect(Object target) throws Exception {
		return ASPECT.get(target) != null;
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Has the Table any items")
	public boolean hasItem(Object target) {
		if (target instanceof IInventory) {
			IInventory inv = (IInventory)target;
			return inv.getStackInSlot(0) != null;
		}
		return false;
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "What aspect does the Table contain")
	public String getAspect(Object target) throws Exception {
		Aspect aspect = ASPECT.get(target);
		return aspect != null? aspect.getTag() : "";
	}

}
