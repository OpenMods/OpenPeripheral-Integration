package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntityNote;
import openperipheral.api.*;

import com.google.common.base.Objects;

public class AdapterNoteBlock implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityNote.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_note";
	}

	@LuaCallable(description = "Increment the pitch of the noteblock")
	public void incrementPitch(TileEntityNote noteblock) {
		noteblock.changePitch();
	}

	@LuaCallable(description = "Play the current note on the noteblock")
	public void triggerNote(TileEntityNote noteblock) {
		noteblock.triggerNote(noteblock.getWorldObj(), noteblock.xCoord, noteblock.yCoord, noteblock.zCoord);
	}

	@LuaCallable(description = "Set the note on the noteblock")
	public void setPitch(TileEntityNote noteblock,
			@Arg(name = "note", description = "The note you want. From 0 to 25") int note)
	{
		noteblock.note = (byte)(note % 25);
		noteblock.markDirty();
	}

	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Get the note currently set on this noteblock")
	public byte getNote(TileEntityNote noteblock) {
		return noteblock.note;
	}

	@LuaCallable(description = "Plays a minecraft sound")
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
