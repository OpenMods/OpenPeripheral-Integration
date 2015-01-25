package openperipheral.integration.appeng;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.*;
import openperipheral.integration.vanilla.ItemFingerprint;
import appeng.api.networking.IGridHost;
import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AdapterNetwork extends AdapterGridBase {

	@Override
	public Class<?> getTargetClass() {
		return IGridHost.class;
	}

	@Override
	public String getSourceId() {
		return "me_network";
	}

	@LuaCallable(description = "Get a list of the stored and craftable items in the network.", returnTypes = LuaReturnType.TABLE)
	public List<?> getAvailableItems(IGridHost host,
			@Env(Constants.ARG_CONVERTER) ITypeConvertersRegistry converter,
			@Optionals @Arg(name = "full", description = "Whether to provide full item information") Boolean full) {
		IStorageGrid storageGrid = getStorageGrid(host);
		final IItemList<IAEItemStack> storageList = storageGrid.getItemInventory().getStorageList();

		List<Object> result = Lists.newArrayList();
		for (IAEItemStack stack : storageList) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)converter.toLua(stack);
			if (full == Boolean.TRUE) map.put("item", stack.getItemStack());
			result.add(map);
		}

		return result;
	}

	@LuaCallable(description = "Retrieves details about the specified item from the ME Network.", returnTypes = LuaReturnType.TABLE)
	public ItemStack getItemDetail(IGridHost host,
			@Arg(name = "item", description = "Details of the item you are looking for: { id, [ dmg, [nbt_hash]] }", type = LuaArgType.TABLE) ItemFingerprint needle) {
		final IItemList<IAEItemStack> items = getStorageGrid(host).getItemInventory().getStorageList();
		final IAEItemStack stack = findStack(items, needle);
		return stack != null? stack.getItemStack() : null;
	}

	@LuaCallable(description = "Get the average power injection into the network", returnTypes = LuaReturnType.NUMBER)
	public double getAvgPowerInjection(IGridHost host) {
		return getEnergyGrid(host).getAvgPowerInjection();
	}

	@LuaCallable(description = "Get the average power usage of the network.", returnTypes = LuaReturnType.NUMBER)
	public double getAvgPowerUsage(IGridHost host) {
		return getEnergyGrid(host).getAvgPowerUsage();
	}

	@LuaCallable(description = "Get the idle power usage of the network.", returnTypes = LuaReturnType.NUMBER)
	public double getIdlePowerUsage(IGridHost host) {
		return getEnergyGrid(host).getIdlePowerUsage();
	}

	@LuaCallable(description = "Get the maximum stored power in the network.", returnTypes = LuaReturnType.NUMBER)
	public double getMaxStoredPower(IGridHost host) {
		return getEnergyGrid(host).getMaxStoredPower();
	}

	@LuaCallable(description = "Get the stored power in the network.", returnTypes = LuaReturnType.NUMBER)
	public double getStoredPower(IGridHost host) {
		return getEnergyGrid(host).getStoredPower();
	}

	@LuaCallable(description = "Get a list of tables representing the available CPUs in the network.", returnTypes = LuaReturnType.TABLE)
	public List<Map<String, Object>> getCraftingCPUs(IGridHost host) {
		ICraftingGrid craftingGrid = getCraftingGrid(host);

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
