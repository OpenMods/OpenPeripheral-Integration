package openperipheral.integration.vanilla;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import net.minecraft.tileentity.TileEntitySign;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.api.helpers.Index;
import org.apache.commons.lang3.StringUtils;

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
		sign.getWorldObj().markBlockForUpdate(sign.xCoord, sign.yCoord, sign.zCoord);
	}

	private static void setLine(TileEntitySign sign, final int index, String text) {
		sign.signText[index] = text.length() < 15? text : text.substring(0, 15);
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Gets the text from the supplied line of the sign")
	public String getLine(TileEntitySign sign,
			@Arg(name = "line", description = "The line number to get from the sign") Index line) {
		line.checkElementIndex("line", sign.signText.length);
		return sign.signText[line.value];
	}

	@ScriptCallable(description = "Sets all text from table")
	public void setLines(TileEntitySign sign, @Arg(name = "lines") String[] lines) {
		final String[] signText = sign.signText;
		for (int i = 0; i < signText.length; i++) {
			final String line = (i < lines.length? Strings.nullToEmpty(lines[i]) : "");
			signText[i] = line;
		}

		updateSign(sign);
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Gets all text as table")
	public String[] getLines(TileEntitySign sign) {
		return sign.signText;
	}

	@ScriptCallable(description = "Sets the text on the sign")
	public void setText(TileEntitySign sign,
			@Arg(name = "text", description = "The text to display on the sign") String text) {
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