package openperipheral.integration.minefactoryreloaded;

import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function0;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterChunkLoader implements IPeripheralAdapter {

	private final Class<?> CLASS = ReflectionHelper.getClass("powercrystals.minefactoryreloaded.tile.machine.TileEntityChunkLoader");

	private final Function0<Short> GET_RADIUS = MethodAccess.create(short.class, CLASS, "getRadius");
	private final Function1<Void, Short> SET_RADIUS = MethodAccess.create(void.class, CLASS, short.class, "setRadius");

	@Override
	public String getSourceId() {
		return "mfr_chunk_loader";
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@ScriptCallable(description = "Get chunk loader radius", returnTypes = ReturnType.NUMBER)
	public short getRadius(Object tileEntityChunkLoader) {
		return GET_RADIUS.call(tileEntityChunkLoader);
	}

	@ScriptCallable(description = "Set chunk loader radius")
	public void setRadius(Object tileEntityChunkLoader, @Arg(name = "radius") short radius) {
		SET_RADIUS.call(tileEntityChunkLoader, radius);
	}

}
