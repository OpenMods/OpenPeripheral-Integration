package openperipheral.integration.mystcraft.v2;

import openmods.Log;

import com.xcompwiz.mystcraft.api.APIInstanceProvider;
import com.xcompwiz.mystcraft.api.exception.APIUndefined;
import com.xcompwiz.mystcraft.api.exception.APIVersionRemoved;
import com.xcompwiz.mystcraft.api.exception.APIVersionUndefined;
import com.xcompwiz.mystcraft.api.hook.*;

public class MystcraftAccess {

	static LinkingAPI linkingApi;

	static LinkPropertyAPI linkPropertiesApi;

	static PageAPI pageApi;

	public static SymbolAPI symbolApi;

	@SuppressWarnings("unchecked")
	public static <T> T getApi(APIInstanceProvider provider, String api) {
		try {
			return (T)provider.getAPIInstance(api);
		} catch (APIUndefined e) {
			Log.warn(e, "Mystraft API %s is not provided. Some functionality may be disabled.");
		} catch (APIVersionUndefined e) {
			Log.warn(e, "Mystraft API %s version is unavalable. Some functionality may be disabled.");
		} catch (APIVersionRemoved e) {
			Log.warn(e, "Mystraft API %s version is unsupported. Some functionality may be disabled.");
		} catch (Exception e) {
			Log.warn(e, "Unknown exception why trying to get Mystcraft API %s", api);
		}

		return null;
	}

	public static void init(APIInstanceProvider provider) {
		linkingApi = getApi(provider, "linking-1");
		linkPropertiesApi = getApi(provider, "linkingprop-1");
		pageApi = getApi(provider, "page-1");
		symbolApi = getApi(provider, "symbol-1");
	}
}
