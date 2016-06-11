package openperipheral.integration.computercraft;

import com.google.common.collect.Maps;
import dan200.computercraft.api.media.IMedia;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.api.meta.IItemStackCustomMetaProvider;

public class MediaMetaProvider implements IItemStackCustomMetaProvider<Item> {

	private final Class<?> API_CLASS = ReflectionHelper.getClass("dan200.computercraft.ComputerCraft");

	private final Function1<IMedia, ItemStack> GET_MEDIA = MethodAccess.create(IMedia.class, API_CLASS, ItemStack.class, "getMedia");

	@Override
	public Class<? extends Item> getTargetClass() {
		return Item.class;
	}

	@Override
	public String getKey() {
		return "disk";
	}

	@Override
	public Object getMeta(Item target, ItemStack stack) {
		if (target instanceof IMedia) return addDiskInfo(stack, (IMedia)target);

		IMedia media = GET_MEDIA.call(null, stack);
		if (media != null) return addDiskInfo(stack, media);

		return null;
	}

	private static Map<String, Object> addDiskInfo(ItemStack stack, IMedia item) {
		Map<String, Object> diskInfo = Maps.newHashMap();
		String label = item.getLabel(stack);
		if (label != null) diskInfo.put("label", label);

		String record = item.getAudioRecordName(stack);
		if (record != null) diskInfo.put("record", record);

		return diskInfo;
	}

	@Override
	public boolean canApply(Item target, ItemStack stack) {
		return (target instanceof IMedia || GET_MEDIA.call(null, stack) != null);
	}

}
