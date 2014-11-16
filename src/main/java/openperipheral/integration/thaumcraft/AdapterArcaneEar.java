package openperipheral.integration.thaumcraft;

import openmods.reflection.FieldAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.*;

@Asynchronous
public class AdapterArcaneEar implements IPeripheralAdapter {
	private final Class<?> CLASS = ReflectionHelper.getClass("thaumcraft.common.tiles.TileSensor");

	private final FieldAccess<Byte> NOTE = FieldAccess.create(CLASS, "note");
	private final FieldAccess<Byte> TONE = FieldAccess.create(CLASS, "tone");

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_ear";
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the note the Ear is set to")
	public byte getNote(Object target) {
		return NOTE.get(target);
	}

	@LuaCallable(description = "Sets the note on the ear")
	public void setNote(Object target, @Arg(description = "Note to set", name = "note", isNullable = false) byte note) {
		NOTE.set(target, note);
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the tone the Ear is set to")
	public byte getTone(Object target) {
		return TONE.get(target);
	}

	@LuaCallable(description = "Sets the tone on the ear")
	public void setTone(Object target, @Arg(description = "Tone to set", name = "tone", isNullable = false) byte tone) {
		TONE.set(target, tone);
	}
}
