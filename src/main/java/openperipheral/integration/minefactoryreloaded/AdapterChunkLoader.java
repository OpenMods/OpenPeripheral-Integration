package openperipheral.integration.minefactoryreloaded;

import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

public class AdapterChunkLoader implements IPeripheralAdapter {

	private static final Class<?> CHUNKLOADER_CLASS = ReflectionHelper.getClass(
			"powercrystals.minefactoryreloaded.tile.machine.TileEntityChunkLoader"
			);

	@Override
	public String getSourceId() {
		return "mfr_chunk_loader";
	}

	@Override
	public Class<?> getTargetClass() {
		return CHUNKLOADER_CLASS;
	}

	@LuaCallable(description = "Get chunk loader radius", returnTypes = LuaReturnType.NUMBER)
	public short getRadius(Object tileEntityChunkLoader) {
		return ReflectionHelper.call(tileEntityChunkLoader, "getRadius");
	}

	@LuaCallable(description = "Set chunk loader radius")
	public void setRadius(Object tileEntityChunkLoader, @Arg(name = "radius") short radius) {
		ReflectionHelper.call(tileEntityChunkLoader, "setRadius", ReflectionHelper.primitive(radius));
	}

}
