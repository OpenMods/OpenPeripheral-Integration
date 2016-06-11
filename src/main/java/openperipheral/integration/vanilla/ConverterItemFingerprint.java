package openperipheral.integration.vanilla;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;
import openperipheral.api.converter.IConverter;
import openperipheral.api.converter.ITypeConverter;

public class ConverterItemFingerprint implements ITypeConverter {

	private static final String TAG_NBT = "nbt_hash";
	private static final String TAG_DMG = "dmg";
	private static final String TAG_ID = "id";

	@Override
	public Object toJava(IConverter registry, Object obj, Class<?> expected) {
		if (!(expected == ItemFingerprint.class && obj instanceof Map)) return null;

		Map<?, ?> map = (Map<?, ?>)obj;
		Object tmp = map.get(TAG_ID);
		if (tmp == null) throw new IllegalArgumentException("Missing mandatory field 'id'");
		if (!(tmp instanceof String)) throw new IllegalArgumentException(String.format("id '%s' is not string", tmp));
		String id = (String)tmp;

		tmp = map.get(TAG_DMG);
		final int dmg = (tmp instanceof Number)? ((Number)tmp).intValue() : -1;

		Object nbtHash = map.get(TAG_NBT);
		return new ItemFingerprint(id, dmg, (String)nbtHash);
	}

	@Override
	public Object fromJava(IConverter registry, Object obj) {
		if (obj instanceof ItemFingerprint) {
			ItemFingerprint fingerprint = (ItemFingerprint)obj;
			Map<String, Object> result = Maps.newHashMap();

			result.put(TAG_ID, fingerprint.id.toString());
			result.put(TAG_DMG, fingerprint.damage);

			if (!Strings.isNullOrEmpty(fingerprint.nbtHash)) result.put(TAG_NBT, fingerprint.nbtHash);

			return result;
		}
		return null;
	}

}
