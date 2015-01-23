package openperipheral.integration.appeng;

import openmods.reflection.MethodAccess;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.IPeripheralAdapter;

import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.storage.IStorageGrid;

import net.minecraftforge.common.util.ForgeDirection;

// Both the general ME Network adapter as well as the ME Interface adapter need
// access to the IGridNode their tile entity is connected to. Extending this class
// gives you a peripheral adapter with access to the grid.
public abstract class AbstractGridAdapter implements IPeripheralAdapter {
	protected final Class<?> CLASS;
	private final MethodAccess.Function1<IGridNode, ForgeDirection> GET_GRIDNODE;

	public AbstractGridAdapter(String className) {
		CLASS = ReflectionHelper.getClass(className);
		GET_GRIDNODE = MethodAccess.create(IGridNode.class, CLASS, ForgeDirection.class, "getGridNode");
	}

	@Override
	public Class<?> getTargetClass() {
		return CLASS;
	}

	// And a few helper methods to ease access to specific parts of the ME
	// Network
	protected IGridNode getGridNode(Object tileEntityController) {
		return GET_GRIDNODE.call(tileEntityController, ForgeDirection.UNKNOWN);
	}

	protected IEnergyGrid getEnergyGrid(Object tileEntityController) {
		return getGridNode(tileEntityController).getGrid().getCache(IEnergyGrid.class);
	}

	protected ICraftingGrid getCraftingGrid(Object tileEntityController) {
		return getGridNode(tileEntityController).getGrid().getCache(ICraftingGrid.class);
	}

	protected IStorageGrid getStorageGrid(Object tileEntityController) {
		return getGridNode(tileEntityController).getGrid().getCache(IStorageGrid.class);
	}
}
