package openperipheral.integration.thaumcraft;

import java.util.List;
import java.util.Map;

import openmods.utils.FieldAccess;
import openmods.utils.ReflectionHelper;
import openperipheral.api.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;

@Asynchronous
public class AdapterJar implements IPeripheralAdapter {

	private static final Class<?> TILE_JAR_FILLABLE_CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileJarFillable");

	private static final FieldAccess<Aspect> ASPECT_FILTER_ACCESS = FieldAccess.create(TILE_JAR_FILLABLE_CLASS, "aspectFilter");

	@Override
	public Class<?> getTargetClass() {
		return TILE_JAR_FILLABLE_CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_jar";
	}

	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Get the aspect filtered by this block block")
	public String getAspectFilter(Object target) {
		Aspect aspect = ASPECT_FILTER_ACCESS.get(target);
		return aspect != null? aspect.getName() : "";
	}

	// special casing jar, for TT compatibility
	@LuaCallable(returnTypes = LuaReturnType.TABLE, description = "Get the Aspects stored in the block")
	public List<Map<String, Object>> getAspects(IAspectContainer container) {
		List<Map<String, Object>> result = ConverterAspectList.aspectsToMap(container.getAspects());
		if (result.isEmpty()) {
			Aspect filter = ASPECT_FILTER_ACCESS.get(container);
			if (filter != null) ConverterAspectList.appendAspectEntry(result, filter, 0);
		}

		return result;
	}

}
