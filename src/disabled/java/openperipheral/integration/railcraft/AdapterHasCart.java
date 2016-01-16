package openperipheral.integration.railcraft;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterHasCart implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("mods.railcraft.common.plugins.buildcraft.triggers.IHasCart");

	private final Function0<Boolean> HAS_CART = MethodAccess.create(boolean.class, CLASS, "hasMinecart");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "railcraft_has_cart";
	}

	@ScriptCallable(description = "Returns true if has cart", returnTypes = ReturnType.BOOLEAN)
	public boolean hasCart(Object target) {
		return HAS_CART.call(target);
	}
}
