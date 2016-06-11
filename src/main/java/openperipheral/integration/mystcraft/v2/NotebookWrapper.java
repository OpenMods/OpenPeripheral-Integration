package openperipheral.integration.mystcraft.v2;

import com.xcompwiz.mystcraft.api.item.IItemPageCollection;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import openmods.fakeplayer.FakePlayerPool;
import openmods.fakeplayer.FakePlayerPool.PlayerUserReturning;
import openmods.fakeplayer.OpenModsFakePlayer;
import openperipheral.api.adapter.AdapterSourceName;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.api.adapter.method.ScriptObject;
import openperipheral.api.helpers.Index;

@ScriptObject
@Asynchronous
@AdapterSourceName("notebook")
public class NotebookWrapper {
	private final WorldServer ownerWorld;

	private IItemPageCollection item;

	private ItemStack stack;

	public NotebookWrapper(WorldServer ownerWorld, IItemPageCollection item, ItemStack stack) {
		super();
		this.ownerWorld = ownerWorld;
		this.item = item;
		this.stack = stack;
	}

	@ScriptCallable(returnTypes = ReturnType.STRING)
	public String getName() {
		return FakePlayerPool.instance.executeOnPlayer(ownerWorld, new PlayerUserReturning<String>() {
			@Override
			public String usePlayer(OpenModsFakePlayer fakePlayer) {
				return item.getDisplayName(fakePlayer, stack);
			}
		});
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, name = "getAllPages")
	public List<ItemStack> getPages() {
		return FakePlayerPool.instance.executeOnPlayer(ownerWorld, new PlayerUserReturning<List<ItemStack>>() {
			@Override
			public List<ItemStack> usePlayer(OpenModsFakePlayer fakePlayer) {
				return item.getItems(fakePlayer, stack);
			}
		});
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER)
	public int getSlotCount(int slot) {
		return getPages().size();
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE)
	public ItemStack getPageFromSlot(@Arg(name = "slot") Index slot) {
		List<ItemStack> pages = getPages();
		slot.checkElementIndex("slot", pages.size());
		return pages.get(slot.value);
	}

	public ItemStack removePage(final ItemStack page) {
		return FakePlayerPool.instance.executeOnPlayer(ownerWorld, new PlayerUserReturning<ItemStack>() {
			@Override
			public ItemStack usePlayer(OpenModsFakePlayer fakePlayer) {
				return item.remove(fakePlayer, stack, page);
			}
		});
	}

	public ItemStack addPage(final ItemStack page) {
		return FakePlayerPool.instance.executeOnPlayer(ownerWorld, new PlayerUserReturning<ItemStack>() {
			@Override
			public ItemStack usePlayer(OpenModsFakePlayer fakePlayer) {
				return item.addPage(fakePlayer, stack, page);
			}
		});
	}
}
