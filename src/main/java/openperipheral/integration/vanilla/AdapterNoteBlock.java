package openperipheral.integration.vanilla;

import com.google.common.base.Objects;
import net.minecraft.tileentity.TileEntityNote;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.Optionals;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterNoteBlock implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityNote.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_note";
	}

	@ScriptCallable(description = "Increment the pitch of the noteblock")
	public void incrementPitch(TileEntityNote noteblock) {
		noteblock.changePitch();
	}

	@ScriptCallable(description = "Play the current note on the noteblock")
	public void triggerNote(TileEntityNote noteblock) {
		noteblock.triggerNote(noteblock.getWorldObj(), noteblock.xCoord, noteblock.yCoord, noteblock.zCoord);
	}

	@ScriptCallable(description = "Set the note on the noteblock", returnTypes = { ReturnType.BOOLEAN })
	public boolean setPitch(TileEntityNote noteblock,
			@Arg(name = "note", description = "The note you want. From 0 to 25") int newNote)
	{
		final byte oldNote = noteblock.note;
		noteblock.note = (byte)(newNote % 25);
		if (!net.minecraftforge.common.ForgeHooks.onNoteChange(noteblock, oldNote)) return false;
		noteblock.markDirty();
		return true;
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Get the note currently set on this noteblock")
	public byte getNote(TileEntityNote noteblock) {
		return noteblock.note;
	}

	@Asynchronous
	@ScriptCallable(description = "Plays a minecraft sound")
	public void playSound(TileEntityNote noteblock,
			@Arg(name = "sound", description = "The identifier for the sound") String name,
			@Arg(name = "pitch", description = "The pitch from 0 to 1") float pitch,
			@Arg(name = "volume", description = "The volume from 0 to 1") float volume,
			@Optionals @Arg(name = "x", description = "X coordinate od sound (relative to block)") Double dx,
			@Arg(name = "y", description = "Y coordinate of sound (relative to block)") Double dy,
			@Arg(name = "z", description = "Z coordinate of sound (relative to block)") Double dz) {
		noteblock.getWorldObj().playSoundEffect(
				noteblock.xCoord + 0.5 + Objects.firstNonNull(dx, 0.0),
				noteblock.yCoord + 0.5 + Objects.firstNonNull(dy, 0.0),
				noteblock.zCoord + 0.5 + Objects.firstNonNull(dz, 0.0),
				name, volume, pitch);
	}
}
