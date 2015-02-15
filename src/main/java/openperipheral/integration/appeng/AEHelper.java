package openperipheral.integration.appeng;

import java.util.HashMap;
import java.util.Map;

import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;

public class AEHelper {

	public static Map<String, Object> cellInfoToMap(ICellInventoryHandler cellinvhandler, ICellInventory cellinv){
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("preformatted", cellinvhandler.isPreformatted());
		ret.put("fuzzy", cellinvhandler.isFuzzy());
		ret.put("freeBytes", cellinv.getFreeBytes());
		ret.put("totalBytes", cellinv.getTotalBytes());
		ret.put("usedBytes",cellinv.getUsedBytes());
		ret.put("totalTypes",cellinv.getTotalItemTypes());
		ret.put("usedTypes",cellinv.getStoredItemTypes());
		ret.put("freeTypes",cellinv.getRemainingItemTypes());
		
		return ret;
	}

}
