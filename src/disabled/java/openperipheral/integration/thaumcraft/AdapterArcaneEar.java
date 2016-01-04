package openperipheral.integration.thaumcraft;

import openmods.reflection.FieldAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

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

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Gets the note the Ear is set to")
	public byte getNote(Object target) {
		return NOTE.get(target);
	}

	@ScriptCallable(description = "Sets the note on the ear")
	public void setNote(Object target, @Arg(description = "Note to set", name = "note", isNullable = false) byte note) {
		NOTE.set(target, note);
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Gets the tone the Ear is set to")
	public byte getTone(Object target) {
		return TONE.get(target);
	}

	@ScriptCallable(description = "Sets the tone on the ear")
	public void setTone(Object target, @Arg(description = "Tone to set", name = "tone", isNullable = false) byte tone) {
		TONE.set(target, tone);
	}
}
