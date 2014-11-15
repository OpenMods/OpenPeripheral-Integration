package openperipheral.integration.minefactoryreloaded;

import java.util.Map;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterHarvester implements IPeripheralAdapter {

	private static final Class<?> HARVESTER_CLASS = ReflectionHelper.getClass(
			"powercrystals.minefactoryreloaded.tile.machine.TileEntityHarvester"
			);

	@Override
	public Class<?> getTargetClass() {
		return HARVESTER_CLASS;
	}

	@Override
	public String getSourceId() {
		return "mfr_";
	}

	@LuaCallable(description = "Get value of shear leaves", returnTypes = LuaReturnType.BOOLEAN)
	public Boolean getShearLeaves(Object tileEntityHarvester) {
		return getSettings(tileEntityHarvester).get("silkTouch");
	}

	@LuaCallable(description = "Get value of harvest small mushrooms", returnTypes = LuaReturnType.BOOLEAN)
	public Boolean getHarvestShrooms(Object tileEntityHarvester) {
		return getSettings(tileEntityHarvester).get("harvestSmallMushrooms");
	}

	@LuaCallable(description = "Set value of shear leaves")
	public void setShearLeaves(Object tileEntityHarvester, @Arg(name = "shearLeaves") boolean shearLeaves) {
		getSettings(tileEntityHarvester).put("silkTouch", shearLeaves);
	}

	@LuaCallable(description = "Set value of harvest small mushrooms")
	public void setHarvestShrooms(Object tileEntityHarvester, @Arg(name = "harvestShrooms") boolean harvestShrooms) {
		getSettings(tileEntityHarvester).put("harvestSmallMushrooms", harvestShrooms);
	}

	private static Map<String, Boolean> getSettings(Object tileEntityHarvester) {
		return ReflectionHelper.call(tileEntityHarvester, "getSettings");
	}

}
