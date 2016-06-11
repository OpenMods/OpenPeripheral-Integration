package openperipheral.integration.minefactoryreloaded;

import java.util.Map;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterHarvester implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityHarvester");

	@SuppressWarnings("rawtypes")
	private final Function0<Map> GET_SETTINGS = MethodAccess.create(Map.class, CLASS, "getSettings");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "mfr_harvester";
	}

	@ScriptCallable(description = "Get value of shear leaves", returnTypes = ReturnType.BOOLEAN)
	public Boolean getShearLeaves(Object tileEntityHarvester) {
		return getSettings(tileEntityHarvester).get("silkTouch");
	}

	@ScriptCallable(description = "Get value of harvest small mushrooms", returnTypes = ReturnType.BOOLEAN)
	public Boolean getHarvestShrooms(Object tileEntityHarvester) {
		return getSettings(tileEntityHarvester).get("harvestSmallMushrooms");
	}

	@ScriptCallable(description = "Set value of shear leaves")
	public void setShearLeaves(Object tileEntityHarvester, @Arg(name = "shearLeaves") boolean shearLeaves) {
		getSettings(tileEntityHarvester).put("silkTouch", shearLeaves);
	}

	@ScriptCallable(description = "Set value of harvest small mushrooms")
	public void setHarvestShrooms(Object tileEntityHarvester, @Arg(name = "harvestShrooms") boolean harvestShrooms) {
		getSettings(tileEntityHarvester).put("harvestSmallMushrooms", harvestShrooms);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Boolean> getSettings(Object tileEntityHarvester) {
		return GET_SETTINGS.call(tileEntityHarvester);
	}

}
