package openperipheral.integration.ic2;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterCrop implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return ICropTile.class;
	}

	@Override
	public String getSourceId() {
		return "ic2_crop";
	}

	@ScriptCallable(returnTypes = ReturnType.STRING)
	public String getID(ICropTile target) {
		final CropCard crop = target.getCrop();
		return crop != null? crop.name() : "";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public byte getSize(ICropTile target) {
		return target.getSize();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's plant growth stat. Higher values indicate faster growth")
	public byte getGrowth(ICropTile target) {
		return target.getGrowth();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's plant gain stat. Higher values indicate more drops")
	public byte getGain(ICropTile target) {
		return target.getGain();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's plant resistance stat. Higher values indicate more resistance against trampling.")
	public byte getResistance(ICropTile target) {
		return target.getResistance();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's plant scan level. Increases every time the seed is analyzed")
	public byte getScanLevel(ICropTile target) {
		return target.getScanLevel();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's nutrient storage. Ranges from 0 to 100.")
	public int getNutrientStorage(ICropTile target) {
		return target.getNutrientStorage();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's hydration storage. 0 indicates nothing, 1-10 indicate water hydration and 11-100 for hydration cells.")
	public int getHydrationStorage(ICropTile target) {
		return target.getHydrationStorage();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getWeedExStorage(ICropTile target) {
		return target.getWeedExStorage();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's humidity. Ranges from 0 (dry) to 10 (humid)")
	public byte getHumidity(ICropTile target) {
		return target.getHumidity();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's nutrient level. Ranges from 0 (empty) to 10 (full)")
	public byte getNutrients(ICropTile target) {
		return target.getNutrients();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the crop's air quality. Ranges from 0 (cluttered) to 10 (fresh)")
	public byte getAirQuality(ICropTile target) {
		return target.getAirQuality();
	}

}
