package openperipheral.integration.vanilla;

import java.util.List;

import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.api.helpers.Index;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class AdapterSign implements IPeripheralAdapter {
	@Override
	public Class<?> getTargetClass() {
		return TileEntitySign.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_sign";
	}

	@ScriptCallable(description = "Sets the text on the sign")
	public void setLine(TileEntitySign sign,
			@Arg(name = "line", description = "The line number to set the text on the sign") Index line,
			@Arg(name = "text", description = "The text to display on the sign") String text) {
		line.checkElementIndex("line", sign.signText.length);
		setLine(sign, line.value, text);
		updateSign(sign);
	}

	private static void updateSign(TileEntitySign sign) {
		sign.getWorld().markBlockForUpdate(sign.getPos());
	}

	private static void setLine(TileEntitySign sign, final int index, String text) {
		sign.signText[index] = new ChatComponentText(text.length() < 15? text : text.substring(0, 15));
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Gets the text from the supplied line of the sign")
	public String getLine(TileEntitySign sign,
			@Arg(name = "line", description = "The line number to get from the sign") Index line)
	{
		line.checkElementIndex("line", sign.signText.length);
		final IChatComponent cc = sign.signText[line.value];
		return cc != null? cc.getUnformattedText() : null;
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Gets the text from the supplied line of the sign")
	public String getFormattedLine(TileEntitySign sign,
			@Arg(name = "line", description = "The line number to get from the sign") Index line)
	{
		line.checkElementIndex("line", sign.signText.length);
		final IChatComponent cc = sign.signText[line.value];
		return cc != null? cc.getFormattedText() : null;
	}

	@ScriptCallable(description = "Sets all text from table")
	public void setLines(TileEntitySign sign, @Arg(name = "lines") String[] lines) {
		final IChatComponent[] signText = sign.signText;
		for (int i = 0; i < signText.length; i++) {
			final String line = (i < lines.length? Strings.nullToEmpty(lines[i]) : "");
			signText[i] = new ChatComponentText(line);
		}

		updateSign(sign);
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Gets all text as table")
	public List<String> getLines(TileEntitySign sign) {
		List<String> result = Lists.newArrayList();
		for (IChatComponent cc : sign.signText)
			result.add(cc != null? cc.getUnformattedText() : "");
		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Gets all text as table")
	public List<String> getFormattedLines(TileEntitySign sign) {
		List<String> result = Lists.newArrayList();
		for (IChatComponent cc : sign.signText)
			result.add(cc != null? cc.getFormattedText() : "");
		return result;
	}

	@ScriptCallable(description = "Sets the text on the sign")
	public void setText(TileEntitySign sign,
			@Arg(name = "text", description = "The text to display on the sign") String text)
	{
		String[] lines = text.split("\n");

		final int newLength = lines.length;
		final int maxLength = sign.signText.length;

		Preconditions.checkArgument(newLength >= 0 && newLength < maxLength, "Value must be in range [1,%s]", maxLength);

		for (int i = 0; i < maxLength; ++i)
			setLine(sign, i, i < newLength? lines[i] : "");

		updateSign(sign);
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Gets the text on the sign")
	public String getText(TileEntitySign sign) {
		return StringUtils.join(sign.signText, '\n');
	}
}