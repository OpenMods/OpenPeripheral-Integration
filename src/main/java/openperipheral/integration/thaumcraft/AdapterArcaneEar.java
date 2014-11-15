package openperipheral.integration.thaumcraft;

import openmods.utils.FieldAccess;
import openmods.utils.ReflectionHelper;
import openperipheral.api.*;

@Asynchronous
public class AdapterArcaneEar implements IPeripheralAdapter {
	private static final Class<?> TILE_ARCANE_EAR = ReflectionHelper.getClass("thaumcraft.common.tiles.TileSensor");

	private static final FieldAccess<Byte> NOTE_ACCESS = FieldAccess.create(TILE_ARCANE_EAR, "note");
	private static final FieldAccess<Byte> TONE_ACCESS = FieldAccess.create(TILE_ARCANE_EAR, "tone");

	@Override
	public Class<?> getTargetClass() {
		return TILE_ARCANE_EAR;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_ear";
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the note the Ear is set to")
	public byte getNote(Object target) {
		return NOTE_ACCESS.get(target);
	}

	@LuaCallable(description = "Sets the note on the ear")
	public void setNote(Object target, @Arg(description = "Note to set", name = "note", isNullable = false) byte note) {
		NOTE_ACCESS.set(target, note);
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Gets the tone the Ear is set to")
	public byte getTone(Object target) {
		return TONE_ACCESS.get(target);
	}

	@LuaCallable(description = "Sets the tone on the ear")
	public void setTone(Object target, @Arg(description = "Tone to set", name = "tone", isNullable = false) byte tone) {
		TONE_ACCESS.set(target, tone);
	}
}
