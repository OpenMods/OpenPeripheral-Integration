package openperipheral.integration.mystcraft.v2;

import openmods.Log;
import openmods.reflection.MethodAccess;
import openmods.reflection.MethodAccess.Function1;
import openmods.reflection.ReflectionHelper;
import openperipheral.integration.OpenPeripheralIntegration;

import com.xcompwiz.mystcraft.api.MystAPI;

public class MystcraftAccess {

	static MystAPI api;

	public static boolean init() {
		try {
			Class<?> cls = ReflectionHelper.getClass("com.xcompwiz.mystcraft.core.InternalAPI");
			Function1<MystAPI, String> getApi = MethodAccess.create(MystAPI.class, cls, String.class, "getAPIInstance");
			api = getApi.call(null, OpenPeripheralIntegration.MOD_ID);
		} catch (Throwable t) {
			Log.warn(t, "Failed to get API");
		}
		return false;
	}
}
