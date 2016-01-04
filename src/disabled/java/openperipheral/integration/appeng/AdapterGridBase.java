package openperipheral.integration.appeng;

import net.minecraftforge.common.util.ForgeDirection;
import openmods.utils.ItemUtils;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.integration.vanilla.ItemFingerprint;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridCache;
import appeng.api.networking.IGridHost;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAETagCompound;
import appeng.api.storage.data.IItemList;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

public abstract class AdapterGridBase implements IPeripheralAdapter {

	protected <T extends IGridCache> T getCache(Class<T> cls, IGridHost host) {
		return host.getGridNode(ForgeDirection.UNKNOWN).getGrid().getCache(cls);
	}

	protected IEnergyGrid getEnergyGrid(IGridHost host) {
		return getCache(IEnergyGrid.class, host);
	}

	protected ICraftingGrid getCraftingGrid(IGridHost host) {
		return getCache(ICraftingGrid.class, host);
	}

	protected IStorageGrid getStorageGrid(IGridHost host) {
		return getCache(IStorageGrid.class, host);
	}

	protected IGrid getGrid(IGridHost host) {
		return host.getGridNode(ForgeDirection.UNKNOWN).getGrid();
	}

	protected static boolean compareToAEStack(ItemFingerprint needle, IAEItemStack hayStack, boolean craftable) {
		return (hayStack.isCraftable() == craftable) && compareToAEStack(needle, hayStack);
	}

	protected static boolean compareToAEStack(ItemFingerprint needle, IAEItemStack hayStack) {
		String id = GameData.getItemRegistry().getNameForObject(hayStack.getItem());
		UniqueIdentifier stackUuid = new UniqueIdentifier(id);
		if (!needle.id.equals(stackUuid)) return false;
		if (needle.damage != -1 && hayStack.getItemDamage() != needle.damage) return false;

		final IAETagCompound aeTag = hayStack.getTagCompound();
		if (aeTag == null) return needle.nbtHash == null;
		if (needle.nbtHash == null) return false;

		// Compare by hash. Not exact (or quick) check, but good enough
		final String nbtHash = ItemUtils.getNBTHash(aeTag.getNBTTagCompoundCopy());
		return nbtHash.equals(needle.nbtHash);
	}

	protected static IAEItemStack findStack(IItemList<IAEItemStack> items, ItemFingerprint fingerprint) {
		for (IAEItemStack stack : items)
			if (compareToAEStack(fingerprint, stack)) return stack;

		return null;
	}

	protected static IAEItemStack findCraftableStack(IItemList<IAEItemStack> items, ItemFingerprint fingerprint) {
		for (IAEItemStack stack : items)
			if (compareToAEStack(fingerprint, stack, true)) return stack;

		return null;
	}
}
