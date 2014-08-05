package openperipheral.integration.vanilla;

import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.item.ItemStack;
import openperipheral.api.IPeripheralAdapter;
import openperipheral.api.LuaMethod;
import openperipheral.api.LuaType;

public class AdapterRecordPlayer implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityJukebox.class;
	}

	@LuaMethod(returnType = LuaType.TABLE, description = "Get the record currently being played")
	public ItemStack getRecord(TileEntityJukebox recordPlayer) {
		return recordPlayer.func_145856_a();
	}

}
