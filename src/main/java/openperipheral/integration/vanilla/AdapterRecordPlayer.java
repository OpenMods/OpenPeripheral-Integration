package openperipheral.integration.vanilla;

import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.item.ItemStack;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterRecordPlayer implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return TileEntityJukebox.class;
	}

	@Override
	public String getSourceId() {
		return "vanilla_player";
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the record currently being played")
	public ItemStack getRecord(TileEntityJukebox recordPlayer) {
		return recordPlayer.func_145856_a();
	}

}
