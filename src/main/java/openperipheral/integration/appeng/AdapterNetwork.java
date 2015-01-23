package openperipheral.integration.appeng;

import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.data.IAEItemStack;

import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.List;

import net.minecraft.item.ItemStack;

public class AdapterNetwork extends AbstractGridAdapter {
	public AdapterNetwork() {
		super("appeng.api.networking.IGridHost");
	}

	@Override
	public String getSourceId() {
		return "me_network";
	}

	@LuaCallable(description = "Get a list of the stored and craftable items in the network.", returnTypes = LuaReturnType.TABLE)
	public List<ItemStack> getAvailableItems(Object tileEntityController) {
		IStorageGrid storageGrid = getStorageGrid(tileEntityController);
		List<ItemStack> items = Lists.newArrayList();
		for (IAEItemStack iaeItemStack : storageGrid.getItemInventory().getStorageList()) {
			items.add(NBTHashMetaProvider.convertStacks(iaeItemStack));
		}

		return items;
	}

	// The following callables are all basic AE-Api <-> OpenPeripheral 1on1s
	// There is not much to say about those. The "complicated" stuff can be
	// found in the AdapterInterface class and the AE2 callbacks it's calling.
	@LuaCallable(description = "Get the average power injection into the network", returnTypes = LuaReturnType.NUMBER)
	public double getAvgPowerInjection(Object tileEntityController) {
		IEnergyGrid energyGrid = getEnergyGrid(tileEntityController);
		return energyGrid.getAvgPowerInjection();
	}

	@LuaCallable(description = "Get the average power usage of the network.", returnTypes = LuaReturnType.NUMBER)
	public double getAvgPowerUsage(Object tileEntityController) {
		IEnergyGrid energyGrid = getEnergyGrid(tileEntityController);
		return energyGrid.getAvgPowerUsage();
	}

	@LuaCallable(description = "Get the idle power usage of the network.", returnTypes = LuaReturnType.NUMBER)
	public double getIdlePowerUsage(Object tileEntityController) {
		IEnergyGrid energyGrid = getEnergyGrid(tileEntityController);
		return energyGrid.getIdlePowerUsage();
	}

	@LuaCallable(description = "Get the maximum stored power in the network.", returnTypes = LuaReturnType.NUMBER)
	public double getMaxStoredPower(Object tileEntityController) {
		IEnergyGrid energyGrid = getEnergyGrid(tileEntityController);
		return energyGrid.getMaxStoredPower();
	}

	@LuaCallable(description = "Get the stored power in the network.", returnTypes = LuaReturnType.NUMBER)
	public double getStoredPower(Object tileEntityController) {
		IEnergyGrid energyGrid = getEnergyGrid(tileEntityController);
		return energyGrid.getStoredPower();
	}

	@LuaCallable(description = "Get a list of tables representing the available CPUs in the network.", returnTypes = LuaReturnType.TABLE)
	public List<Map<String, Object>> getCraftingCPUs(Object tileEntityController) {
		ICraftingGrid craftingGrid = getCraftingGrid(tileEntityController);

		// This is a simple Object -> Map mapping. This could be a converter,
		// but this is
		// good enough for now.
		List<Map<String, Object>> cpus = Lists.newArrayList();
		for (ICraftingCPU cpu : craftingGrid.getCpus()) {
			Map<String, Object> cpuDetails = Maps.newHashMap();
			cpuDetails.put("name", cpu.getName());
			cpuDetails.put("storage", cpu.getAvailableStorage());
			cpuDetails.put("coprocessors", cpu.getCoProcessors());
			cpuDetails.put("busy", cpu.isBusy());
			cpus.add(cpuDetails);
		}

		return cpus;
	}
}
