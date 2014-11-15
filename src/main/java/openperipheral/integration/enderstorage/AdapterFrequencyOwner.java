package openperipheral.integration.enderstorage;

import net.minecraft.tileentity.TileEntity;
import openmods.Log;
import openmods.utils.*;
import openmods.utils.ColorUtils.ColorMeta;
import openperipheral.api.*;

import com.google.common.base.Preconditions;

public class AdapterFrequencyOwner implements IPeripheralAdapter {
	private static final Class<?> CLAZZ = ReflectionHelper.getClass("codechicken.enderstorage.common.TileFrequencyOwner");

	private final FieldAccess<Integer> freq = FieldAccess.create(CLAZZ, "freq");

	@Override
	public Class<?> getTargetClass() {
		return CLAZZ;
	}

	@Override
	public String getSourceId() {
		return "ender_frequency";
	}

	@Asynchronous
	@Alias("getColours")
	@LuaCallable(returnTypes = { LuaReturnType.NUMBER, LuaReturnType.NUMBER, LuaReturnType.NUMBER },
			description = "Get the colours active on this chest or tank")
	public IMultiReturn getColors(TileEntity frequencyOwner) {
		// get the current frequency
		int frequency = freq.get(frequencyOwner);
		// return a map of the frequency in ComputerCraft colour format
		return MultiReturn.wrap(
				1 << (frequency >> 8 & 0xF),
				1 << (frequency >> 4 & 0xF),
				1 << (frequency >> 0 & 0xF));
	}

	@Asynchronous
	@LuaCallable(returnTypes = { LuaReturnType.STRING, LuaReturnType.STRING, LuaReturnType.STRING },
			description = "Get the colours active on this chest or tank")
	public IMultiReturn getColorNames(TileEntity frequencyOwner) {
		int frequency = freq.get(frequencyOwner);
		return MultiReturn.wrap(
				colorToName(frequency >> 8 & 0xF),
				colorToName(frequency >> 4 & 0xF),
				colorToName(frequency >> 0 & 0xF));
	}

	@Alias("setColours")
	@LuaCallable(description = "Set the frequency of this chest or tank")
	public void setColors(TileEntity frequencyOwner,
			@Arg(name = "color_left", description = "The first color") int colorLeft,
			@Arg(name = "color_middle", description = "The second color") int colorMiddle,
			@Arg(name = "color_right", description = "The third color") int colorRight) {
		// transform the ComputerCraft colours (2^n) into the range 0-15 And
		// validate they're within this range
		int high = parseComputerCraftColor(colorLeft);
		int med = parseComputerCraftColor(colorMiddle);
		int low = parseComputerCraftColor(colorRight);

		int frequency = ((high & 0xF) << 8) + ((med & 0xF) << 4) + (low & 0xF);
		Log.info("%06X", frequency);
		setFreq(frequencyOwner, frequency);
	}

	@LuaCallable(description = "Set the frequency of this chest or tank")
	public void setColorNames(TileEntity frequencyOwner,
			@Arg(name = "color_left") String colorLeft,
			@Arg(name = "color_middle") String colorMiddle,
			@Arg(name = "color_right") String colorRight) {

		int high = parseColorName(colorLeft);
		int med = parseColorName(colorMiddle);
		int low = parseColorName(colorRight);
		// convert the three colours into a single colour
		int frequency = ((high & 0xF) << 8) + ((med & 0xF) << 4) + (low & 0xF);
		// set the TE's frequency to the new colours
		Log.info("%06X", frequency);
		setFreq(frequencyOwner, frequency);
	}

	@Asynchronous
	@LuaCallable(returnTypes = LuaReturnType.NUMBER, description = "Get the frequency of this chest or tank")
	public int getFrequency(TileEntity frequencyOwner) {
		return freq.get(frequencyOwner);
	}

	@LuaCallable(description = "Set the frequency of this chest or tank")
	public void setFrequency(TileEntity frequencyOwner,
			@Arg(name = "frequency", description = "A single number that represents all three colours on this chest or tank") int frequency) {
		setFreq(frequencyOwner, frequency);
	}

	private static void setFreq(TileEntity frequencyOwner, int frequency) {
		Preconditions.checkElementIndex(frequency, 4096, "frequency");
		ReflectionHelper.call(frequencyOwner, "setFreq", ReflectionHelper.primitive(frequency));
	}

	private static int parseComputerCraftColor(int bitmask) {
		ColorMeta meta = ColorUtils.bitmaskToColor(bitmask);
		Log.info("%s", meta.name);
		Preconditions.checkNotNull(meta, "Invalid color %sb", Integer.toBinaryString(bitmask));
		return meta.vanillaBlockId;
	}

	private static int parseColorName(String name) {
		ColorMeta meta = ColorUtils.nameToColor(name);
		Preconditions.checkNotNull(meta, "Invalid color name %s", name);
		return meta.vanillaBlockId;
	}

	private static String colorToName(int color) {
		ColorMeta meta = ColorUtils.vanillaBlockToColor(color);
		Preconditions.checkNotNull(meta, "Invalid color id %s", color);
		return meta.name;
	}
}
