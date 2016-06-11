package openperipheral.integration.thaumcraft;

import java.util.List;
import java.util.Map;
import openmods.reflection.FieldAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;

@Asynchronous
public class AdapterJar implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileJarFillable");

	private final FieldAccess<Aspect> ASPECT_FILTER = FieldAccess.create(CLASS, "aspectFilter");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_jar";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the aspect filtered by this block block")
	public String getAspectFilter(Object target) {
		Aspect aspect = ASPECT_FILTER.get(target);
		return aspect != null? aspect.getName() : "";
	}

	// special casing jar, for TT compatibility
	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the Aspects stored in the block")
	public List<Map<String, Object>> getAspects(IAspectContainer container) {
		List<Map<String, Object>> result = ConverterAspectList.aspectsToMap(container.getAspects());
		if (result.isEmpty()) {
			Aspect filter = ASPECT_FILTER.get(container);
			if (filter != null) ConverterAspectList.appendAspectEntry(result, filter, 0);
		}

		return result;
	}

}
