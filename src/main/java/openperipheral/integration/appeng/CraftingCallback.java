package openperipheral.integration.appeng;

import java.util.List;

import openperipheral.api.IArchitectureAccess;
import openperipheral.api.ITypeConvertersRegistry;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.crafting.*;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

import com.google.common.collect.Lists;

public class CraftingCallback implements ICraftingCallback {
	private final IArchitectureAccess access;
	private final ITypeConvertersRegistry converter;

	private final IMEMonitor<IAEItemStack> monitor;
	private final MachineSource source;
	private final IActionHost actionHost;
	private final ICraftingGrid craftingGrid;
	private final ICraftingCPU wantedCpu;

	private final Object requestedStack;

	public CraftingCallback(IArchitectureAccess access, ITypeConvertersRegistry converter, ICraftingGrid craftingGrid, IMEMonitor<IAEItemStack> monitor, IActionHost actionHost, ICraftingCPU wantedCpu, IAEItemStack requestedStack) {
		this.access = access;
		this.converter = converter;
		this.monitor = monitor;
		this.source = new MachineSource(actionHost);
		this.actionHost = actionHost;
		this.craftingGrid = craftingGrid;
		this.wantedCpu = wantedCpu;
		this.requestedStack = converter.toLua(requestedStack);
	}

	@Override
	public void calculationComplete(ICraftingJob job) {
		// IsSimulation is true when the job cannot be processed because items
		// are missing, i.e.
		// not available in the system or not recursively craftable.
		// In that case we will send an event to the computer with the missing
		// ingredients.
		if (job.isSimulation()) {
			sendSimulationInfo(job);
		} else {
			// All items are available, we can start crafting now
			CraftingRequester craftingRequester = new CraftingRequester(actionHost, access, requestedStack);
			craftingGrid.submitJob(job, craftingRequester, wantedCpu, false, source);
		}
	}

	private void sendSimulationInfo(ICraftingJob job) {
		// Grab the list of items from the job (this is basically the same
		// list the ME Terminal shows when crafting an item).
		IItemList<IAEItemStack> plan = AEApi.instance().storage().createItemList();
		job.populatePlan(plan);

		// This procedure to determine whether an item is missing is
		// basically the same as
		// the one used by AE2. Taken from here:
		// https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/rv2/src/main/java/appeng/container/implementations/ContainerCraftConfirm.java
		List<IAEItemStack> missingItems = Lists.newArrayList();
		for (IAEItemStack needed : plan) {
			IAEItemStack toExtract = needed.copy();

			// Not sure why this is needed, but AE2 does it itself.
			toExtract.reset();
			toExtract.setStackSize(needed.getStackSize());

			// Simulate the extraction, this is basically a "fast" way to
			// check whether an item exists in the first place.
			// The idea is: if we can extract it, we can use it for crafting.
			IAEItemStack extracted = monitor.extractItems(toExtract, Actionable.SIMULATE, source);

			// If we could not extract the item, we are missing all of the
			// quantity that's required.
			// Otherwise we are only missing the difference between the two.
			// This can be 0 if we were able to extract all of the required items.
			long missing = needed.getStackSize();
			if (extracted != null) missing -= extracted.getStackSize();

			if (missing > 0) {
				IAEItemStack missingStack = needed.copy();
				missingItems.add(missingStack);
			}
		}

		// At this point missingItems should always have at least one
		// element, because isSimulation would return false.
		// But even if it's empty, we need to send event to unblock process

		access.signal(ModuleAppEng.CC_EVENT_STATE_CHANGED,
				converter.toLua(this.requestedStack),
				"missing_items",
				converter.toLua(missingItems));
	}
}
