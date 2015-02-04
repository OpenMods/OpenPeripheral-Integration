package openperipheral.integration.thaumcraft;

import java.util.Map;

import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

@Asynchronous
public class AdapterAspectContainer implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IAspectContainer.class;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_aspect_container";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the Aspects stored in the block")
	public AspectList getAspects(IAspectContainer container) {
		return container.getAspects();
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the map of aspects stored in the block (summed, if there are multiple entries)")
	public Map<String, Integer> getAspectsSum(IAspectContainer container) {
		AspectList aspectList = container.getAspects();
		if (aspectList == null) return null;
		Map<String, Integer> result = Maps.newHashMap();
		for (Aspect aspect : aspectList.getAspects()) {
			if (aspect == null) continue;
			String name = aspect.getName();
			int amount = Objects.firstNonNull(result.get(name), 0);
			result.put(name, amount + aspectList.getAmount(aspect));
		}
		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get amount of specific aspect stored in this block")
	public int getAspectCount(IAspectContainer container,
			@Arg(name = "aspect", description = "Aspect to be checked") String aspectName) {

		Aspect aspect = Aspect.getAspect(aspectName.toLowerCase());
		Preconditions.checkNotNull(aspect, "Invalid aspect name");
		AspectList list = container.getAspects();
		if (list == null) return 0;
		return list.getAmount(aspect);
	}
}
