package openperipheral.integration.vanilla;

import net.minecraft.tileentity.TileEntitySign;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

public class AdapterSign implements IPeripheralAdapter {
	@Override
	public Class<?> getTargetClass() {
		return TileEntitySign.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_sign";
	}

	private static void checkRange(int value, int max) {
		Preconditions.checkArgument(value >= 0 && value < max, "Value must be in range [1,%s]", max);
	}

	@ScriptCallable(description = "Sets the text on the sign")
	public void setLine(TileEntitySign sign,
			@Arg(name = "line", description = "The line number to set the text on the sign") int line,
			@Arg(name = "text", description = "The text to display on the sign") String text) {
		line -= 1;
		checkRange(line, sign.signText.length);
		sign.signText[line] = text.length() < 15? text : text.substring(0, 15);
		sign.getWorldObj().markBlockForUpdate(sign.xCoord, sign.yCoord, sign.zCoord);
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Gets the text from the supplied line of the sign")
	public String getLine(TileEntitySign sign,
			@Arg(name = "line", description = "The line number to get from the sign") int line)
	{
		line -= 1;
		checkRange(line, sign.signText.length);
		return sign.signText[line];
	}

	@ScriptCallable(description = "Sets the text on the sign")
	public void setText(TileEntitySign sign,
			@Arg(name = "text", description = "The text to display on the sign") String text)
	{
		String[] lines = text.split("\n");

		int currLength = lines.length;
		int maxLength = sign.signText.length;

		checkRange(currLength, maxLength);

		for (int i = 0; i < maxLength; ++i)
			setLine(sign, i + 1, i < currLength? lines[i] : "");
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Gets the text on the sign")
	public String getText(TileEntitySign sign) {
		return StringUtils.join(sign.signText, '\n');
	}
}