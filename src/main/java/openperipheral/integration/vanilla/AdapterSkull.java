package openperipheral.integration.vanilla;

import com.mojang.authlib.GameProfile;
import net.minecraft.tileentity.TileEntitySkull;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.MultipleReturn;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterSkull implements IPeripheralAdapter {

	@Override
	public String getSourceId() {
		return "skull";
	}

	@Override
	public Class<?> getTargetClass() {
		return TileEntitySkull.class;
	}

	@ScriptCallable(returnTypes = ReturnType.STRING)
	public String getType(TileEntitySkull skull) {
		int skullType = skull.func_145904_a();

		switch (skullType) {
			case 0:
				return "skeleton";
			case 1:
				return "wither_skeleton";
			case 2:
				return "zombie";
			case 3:
				return "player";
			case 4:
				return "creeper";
		}

		return "unknown";
	}

	@MultipleReturn
	@ScriptCallable(returnTypes = { ReturnType.STRING, ReturnType.STRING })
	public Object[] getPlayer(TileEntitySkull skull) {
		if (skull.func_145904_a() != 3) return new Object[] { null, null };

		GameProfile profile = skull.func_152108_a();
		if (profile == null) return new Object[] { null, null };

		return new Object[] { profile.getId(), profile.getName() };
	}
}
