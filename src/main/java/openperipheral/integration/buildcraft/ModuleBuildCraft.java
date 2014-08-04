package openperipheral.integration.buildcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.integration.OPIntegrationModule;
import buildcraft.api.transport.IPipeTile;

public class ModuleBuildCraft extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.BUILDCRAFT;
	}

	@Override
	public void load() {
		IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterMachine());
		adapterRegistry.register(new AdapterPowerReceptor());
		adapterRegistry.register(new AdapterPipe());
	}

	public static int tryAcceptIntoPipe(TileEntity possiblePipe, ItemStack nextStack, ForgeDirection direction) {
		if (possiblePipe instanceof IPipeTile) { return ((IPipeTile)possiblePipe).injectItem(nextStack, true, direction.getOpposite()); }
		return 0;
	}
}
