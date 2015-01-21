package openperipheral.integration.appeng;

import appeng.api.storage.data.IAEItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;

// SearchNeedle is a helper class meant to allow easier communication between
// lua requesting access to a specific item from the network. It is able to
// compare itself to an IAEItemStack, including its nbt data.
public class SearchNeedle {
	int meta = 0;
	String id;
	String nbtHash;
	String modId;
	String name;
	Item item;
	GameRegistry.UniqueIdentifier uuid;

	public SearchNeedle(String id, int meta, String nbtHash) {
		this.id = id;
		this.meta = meta;
		this.nbtHash = nbtHash;

		// Prepare a few values for easier consumption later on
		String[] parts = id.split(":");
		this.modId = parts[0];
		this.name = parts[1];

		this.item = GameRegistry.findItem(modId, name);
		this.uuid = GameRegistry.findUniqueIdentifierFor(this.item);
	}

	public SearchNeedle(String id, int meta) {
		this(id, meta, null);
	}

	public SearchNeedle(String id) {
		this(id, 0);
	}

	public boolean compareToAEStack(IAEItemStack hayStack) {
		// If we cannot calculate a UUID to the item that got passed on,
		// something is really
		// wrong. Or the user is simply requesting an non existing item. Abort.
		if (this.uuid == null) { return false; }

		// Compare the uuids provided by the GameRegistry
		GameRegistry.UniqueIdentifier stackUuid = GameRegistry
				.findUniqueIdentifierFor(hayStack.getItem());
		if (stackUuid == null
				|| !stackUuid.toString().equals(this.uuid.toString())) { return false; }

		// Compare the meta data
		if (hayStack.getItemDamage() != this.meta) { return false; }

		// Compare the nbtHash. More details about this can be found in the
		// NBTHashMetaProvider
		if (this.nbtHash != null && hayStack.hasTagCompound()) {
			if (!this.nbtHash.equals(NBTHashMetaProvider.getNBTHash(hayStack
					.getTagCompound()))) { return false; }
		}

		// Survived all checks. This must be the item we are looking for.
		return true;
	}
}
