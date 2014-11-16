/**
 * 
 */
package openperipheral.integration.thaumcraft;

import java.lang.reflect.Field;

import net.minecraft.inventory.IInventory;
import openmods.reflection.FieldAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.*;
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

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Does the Table have an aspect in it")
	public boolean hasAspect(Object target) throws Exception {
		Field f = ReflectionHelper.getField(CLASS, "aspect");
		return f.get(target) != null;
	}

	@LuaCallable(returnTypes = LuaReturnType.BOOLEAN, description = "Has the Table any items")
	public boolean hasItem(Object target) {
		if (target instanceof IInventory)
		{
			IInventory inv = (IInventory)target;
			return inv.getStackInSlot(0) != null;
		}
		return false;
	}

	@LuaCallable(returnTypes = LuaReturnType.STRING, description = "What aspect does the Table contain")
	public String getAspect(Object target) throws Exception {
		Aspect aspect = ASPECT.get(target);
		return aspect != null? aspect.getTag() : "";
	}

}
