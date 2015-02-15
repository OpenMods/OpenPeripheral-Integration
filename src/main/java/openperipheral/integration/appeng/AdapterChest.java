package openperipheral.integration.appeng;

import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;

import org.apache.logging.log4j.Logger;

import appeng.api.AEApi;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.networking.IGridHost;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import cpw.mods.fml.common.FMLLog;

public class AdapterChest extends AbstractGridAdapter{
	Logger logger = FMLLog.getLogger();
	
	public AdapterChest(){
		super("appeng.tile.storage.TileChest");
	}
	
	@Override
	public String getSourceId() {
		return "me_chest";
	}
	
	
	@LuaCallable(description = "Returns a table with information concerning the cell in the attached MEChest. If the chest is empty the method returns nil.", returnTypes = LuaReturnType.TABLE)
	public Map<String, Object> getCellInfo(IGridHost gridHost) {
		
		IInventory inv = (IInventory) gridHost;
		ItemStack itemstack = inv.getStackInSlot(1);//if there is a cell in the drive, it is locatated at position 2 of the inventory.
		if(itemstack != null){
			Item item = itemstack.getItem();
			if(item instanceof IStorageCell){//there is a storage cell in the chest
				IMEInventoryHandler inventory = AEApi.instance().registries().cell().getCellInventory( itemstack, null, StorageChannel.ITEMS );//get the inventory handler from ae api
				if (inventory instanceof ICellInventoryHandler){
					ICellInventoryHandler handler = (ICellInventoryHandler) inventory;
					ICellInventory cellInventory = handler.getCellInv();
					if (cellInventory != null){
						Map<String, Object> luarepr = AEHelper.cellInfoToMap(handler, cellInventory); //fill in the array with cell information
						luarepr.put("name", itemstack.getDisplayName()); //add the of the drive, usefull if drive is renamed
						return luarepr;
					}
				}
			}
		}
		return null;//if there is no cell attached, we return null
		
	}
	

}
