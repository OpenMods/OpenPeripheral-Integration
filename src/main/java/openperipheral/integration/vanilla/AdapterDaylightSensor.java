package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

import com.google.common.base.Preconditions;

public class AdapterDaylightSensor implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityDaylightDetector.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_daylight_sensor";
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Get true if age has normal sky")
	public boolean hasSky(TileEntityDaylightDetector target) {
		World world = target.getWorld();
		return !world.provider.getHasNoSky();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get level of natural light (sky)")
	public int getSkyLight(TileEntityDaylightDetector target) {
		World world = target.getWorld();
		Preconditions.checkArgument(!world.provider.getHasNoSky(), "World has no sky");
		return world.getLightFor(EnumSkyBlock.SKY, target.getPos()) - world.getSkylightSubtracted();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get level of block light")
	public int getBlockLight(TileEntityDaylightDetector target) {
		World world = target.getWorld();
		return world.getLightFor(EnumSkyBlock.BLOCK, target.getPos());
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get angle of sun (in degrees, 0 is zenith)")
	public float getCelestialAngle(TileEntityDaylightDetector target) {
		World world = target.getWorld();
		return world.getCelestialAngle(1) * 360;
	}
}
