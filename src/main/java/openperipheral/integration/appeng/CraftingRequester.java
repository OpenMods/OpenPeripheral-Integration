package openperipheral.integration.appeng;

import appeng.api.config.Actionable;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingLink;
import appeng.api.networking.crafting.ICraftingRequester;
import appeng.api.networking.security.IActionHost;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;

import dan200.computercraft.api.peripheral.IComputerAccess;

import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.ImmutableSet;

public class CraftingRequester implements ICraftingRequester {
	IActionHost original;
	IComputerAccess computer;
	Object requestedStack;

	public CraftingRequester(IComputerAccess computer, IActionHost original, Object requestedStack) {
		this.computer = computer;
		this.original = original;
		this.requestedStack = requestedStack;
	}

	@Override
	// We want to put the crafted items straight back into the AE network.
	// We could put them directly into the inventory we are pointing at, but
	// that would introduce two problems:
	// a) we have to care about whether there actually is an inventory.
	// b) maybe the user does not want the items in the inventory in the first
	// place, and if he does, he can simply extract them as soon as he received
	// the event
	public IAEItemStack injectCraftedItems(ICraftingLink link, IAEItemStack items, Actionable mode) {
		return items;
	}

	// We broadcast the two possible events to the computer, nothing fancy about
	// it.
	// We're passing the already toLua converted itemstack we requested in the
	// first place as an argument.
	@Override
	public void jobStateChange(ICraftingLink link) {
		if (link.isCanceled()) {
			computer.queueEvent(ModuleAppEng.CC_EVENT_STATE_CHANGED, new Object[] { requestedStack, "canceled" });
		} else if (link.isDone()) {
			computer.queueEvent(ModuleAppEng.CC_EVENT_STATE_CHANGED, new Object[] { requestedStack, "done" });
		}
	}

	// This method should never be called on our instance since it's never
	// really part of the ME grid in the first place. The only method that
	// would get called is the one from the original version; we can safely
	// ignore this method and just return null.
	@Override
	public ImmutableSet<ICraftingLink> getRequestedJobs() {
		return null;
	}

	// The following methods are inherited by ICraftingRequester from
	// IActionHost, so we can just forward the calls to the real thing.
	@Override
	public IGridNode getActionableNode() {
		return original.getActionableNode();
	}

	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		return original.getGridNode(dir);
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return original.getCableConnectionType(dir);
	}

	@Override
	public void securityBreak() {
		original.securityBreak();
	}
}
