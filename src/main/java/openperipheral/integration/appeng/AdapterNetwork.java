package openperipheral.integration.appeng;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.Constants;
import openperipheral.api.adapter.method.*;
import openperipheral.api.converter.IConverter;
import openperipheral.integration.OpcAccess;
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

	public static enum ItemDetails {
		NONE, PROXY, ALL
	}

	@ScriptCallable(description = "Get a list of the stored and craftable items in the network.", returnTypes = ReturnType.TABLE)
	public List<?> getAvailableItems(IGridHost host,
			@Env(Constants.ARG_CONVERTER) IConverter converter,
			@Optionals @Arg(name = "details", description = "Format of stored items details (defalt: none)") ItemDetails format) {
		IStorageGrid storageGrid = getStorageGrid(host);
		final IItemList<IAEItemStack> storageList = storageGrid.getItemInventory().getStorageList();

		List<Object> result = Lists.newArrayList();
		for (IAEItemStack stack : storageList) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)converter.fromJava(stack);
			if (format != null && format != ItemDetails.NONE) {
				final ItemStack itemStack = stack.getItemStack();
				if (format == ItemDetails.PROXY) map.put("item", OpcAccess.itemStackMetaBuilder.createProxy(itemStack));
				else if (format == ItemDetails.ALL) map.put("item", itemStack);
			}
			result.add(map);
		}

		return result;
	}

	@ScriptCallable(description = "Retrieves details about the specified item from the ME Network.", returnTypes = ReturnType.OBJECT)
	public Object getItemDetail(IGridHost host,
			@Arg(name = "item", description = "Details of the item you are looking for") ItemFingerprint needle,
			@Optionals @Arg(name = "proxy", description = "If false, method will compute whole table, instead of returning proxy") Boolean proxy)
	{
		final IItemList<IAEItemStack> items = getStorageGrid(host).getItemInventory().getStorageList();
		final IAEItemStack stack = findStack(items, needle);
		if (stack == null) return null;
		ItemStack vanillaStack = stack.getItemStack();
		return proxy != Boolean.FALSE? OpcAccess.itemStackMetaBuilder.createProxy(vanillaStack) : vanillaStack;
	}

	@ScriptCallable(description = "Get the average power injection into the network", returnTypes = ReturnType.NUMBER)
	public double getAvgPowerInjection(IGridHost host) {
		return getEnergyGrid(host).getAvgPowerInjection();
	}

	@ScriptCallable(description = "Get the average power usage of the network.", returnTypes = ReturnType.NUMBER)
	public double getAvgPowerUsage(IGridHost host) {
		return getEnergyGrid(host).getAvgPowerUsage();
	}

	@ScriptCallable(description = "Get the idle power usage of the network.", returnTypes = ReturnType.NUMBER)
	public double getIdlePowerUsage(IGridHost host) {
		return getEnergyGrid(host).getIdlePowerUsage();
	}

	@ScriptCallable(description = "Get the maximum stored power in the network.", returnTypes = ReturnType.NUMBER)
	public double getMaxStoredPower(IGridHost host) {
		return getEnergyGrid(host).getMaxStoredPower();
	}

	@ScriptCallable(description = "Get the stored power in the network.", returnTypes = ReturnType.NUMBER)
	public double getStoredPower(IGridHost host) {
		return getEnergyGrid(host).getStoredPower();
	}

	@ScriptCallable(description = "Get a list of tables representing the available CPUs in the network.", returnTypes = ReturnType.TABLE)
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
