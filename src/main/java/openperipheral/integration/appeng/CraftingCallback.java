package openperipheral.integration.appeng;

import openperipheral.api.ApiAccess;
import openperipheral.api.ITypeConvertersRegistry;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.crafting.ICraftingCallback;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.crafting.ICraftingJob;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

import dan200.computercraft.api.peripheral.IComputerAccess;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import java.util.List;

public class CraftingCallback implements ICraftingCallback {
	IComputerAccess computer;
	IMEMonitor<IAEItemStack> monitor;
	MachineSource source;
	IActionHost actionHost;
	ICraftingGrid craftingGrid;
	ICraftingCPU wantedCpu;

	Object requestedStack;

	public CraftingCallback(IComputerAccess computer, ICraftingGrid craftingGrid, IMEMonitor<IAEItemStack> monitor, IActionHost actionHost, ICraftingCPU wantedCpu, IAEItemStack requestedStack) {
		this.computer = computer;
		this.monitor = monitor;
		this.source = new MachineSource(actionHost);
		this.actionHost = actionHost;
		this.craftingGrid = craftingGrid;
		this.wantedCpu = wantedCpu;

		// As we only need the requested stack so we can pass it back to lua
		// program we can convert it right here, right now.
		this.requestedStack = ApiAccess.getApi(ITypeConvertersRegistry.class).toLua(NBTHashMetaProvider.convertStacks(requestedStack));
	}

	@Override
	public void calculationComplete(ICraftingJob job) {
		// IsSimulation is true when the job cannot be processed because items
		// are missing, i.e.
		// not available in the system or not recursively craftable.
		// In that case we will send an event to the computer with the missing
		// ingredients.
		if (job.isSimulation()) {
			// Prepare the list of items we will pass in the event later on
			List<ItemStack> items = Lists.newArrayList();

			// Grab the list of items from the job (this is basically the same
			// list the
			// ME Terminal shows when crafting an item).
			IItemList<IAEItemStack> plan = AEApi.instance().storage().createItemList();
			job.populatePlan(plan);

			// This procedure to determine whether an item is missing is
			// basically the same as
			// the one used by AE2. Taken from here:
			// https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/rv2/src/main/java/appeng/container/implementations/ContainerCraftConfirm.java
			List<ItemStack> missingItems = Lists.newArrayList();
			for (IAEItemStack stack : plan) {
				// "stack" is the stack + quantity we need

				// "missing" will hold the quantity of items we are missing
				IAEItemStack missing = stack.copy();

				// "extract" will hold the quantity of items we can extract from
				// the system.
				IAEItemStack extract = stack.copy();

				// Not sure why this is needed, but AE2 does it itself.
				extract.reset();
				extract.setStackSize(stack.getStackSize());

				// Simulate the extraction, this is basically a "fast" way to
				// check whether an item
				// exists in the first place. And it's quantity. The idea is: if
				// we can extract it,
				// we can use it for crafting.
				extract = monitor.extractItems(extract, Actionable.SIMULATE, source);

				// If we could not extract the item, we are missing all of the
				// quantity that's required.
				// Otherwise we are only missing the difference between the two.
				// This can be 0 if we
				// were able to extract all of the required items.
				if (extract == null) {
					missing.setStackSize(stack.getStackSize());
				} else {
					missing.setStackSize(stack.getStackSize() - extract.getStackSize());
				}

				// So we only need to report items where we are actually missing
				// some quantity.
				if (missing.getStackSize() > 0) {
					missingItems.add(NBTHashMetaProvider.convertStacks(missing));
				}
			}

			// At this point missingItems should always have at least one
			// element, because isSimulation would
			// return false. But we will still validate it for good measure,
			// maybe something else went wrong
			// on the way.
			// When we did we can finally send an event to the call computer
			// telling him about the missing stuff
			// and that we cannot proceed.
			if (missingItems.size() > 0) {
				// Assemble the result we're going to pass with the event. This
				// is using OpenPeripherals
				// Type conversion to include all the data we're used to having.
				Object[] result = new Object[] {
						this.requestedStack,
						"missing_items",
						ApiAccess.getApi(ITypeConvertersRegistry.class).toLua(missingItems)
				};

				computer.queueEvent(ModuleAppEng.CC_EVENT_STATE_CHANGED, result);
			}

			return;
		}

		// All items are available, we can start crafting now
		// We are using our own ICraftingRequester so we can easily track the
		// state of the job
		// and forward events to the computer as needed.
		CraftingRequester craftingRequester = new CraftingRequester(computer, actionHost, requestedStack);
		craftingGrid.submitJob(job, craftingRequester, wantedCpu, false, source);
	}
}
