package openperipheral.integration.appeng;

import openperipheral.api.ITypeConverter;
import openperipheral.api.ITypeConvertersRegistry;

import java.util.Map;

public class ConverterSearchNeedle implements ITypeConverter {

	@Override
	public Object fromLua(ITypeConvertersRegistry registry, Object obj, Class<?> expected) {
		if (!(expected == SearchNeedle.class && obj instanceof Map)) { return null; }

		Map<Object, Object> map = (Map<Object, Object>)obj;
		Object id = map.get("id");
		if (id == null) { return null; }

		String[] parts = ((String)id).split(":", 2);

		int dmg = getIntValue(map, "dmg", 0);

		String nbtHash = null;
		if (map.containsKey("nbt_id")) {
			nbtHash = (String)map.get("nbt_id");
		}

		return new SearchNeedle((String)id, dmg, nbtHash);
	}

	@Override
	public Object toLua(ITypeConvertersRegistry registry, Object obj) {
		return null;
	}

	private static int getIntValue(Map<?, ?> map, String key, int _default) {
		Object value = map.get(key);
		if (value instanceof Number) { return ((Number)value).intValue(); }

		return _default;
	}
}
