package openperipheral.integration.appeng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openperipheral.api.LuaCallable;
import openperipheral.api.LuaReturnType;
import appeng.api.AEApi;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.networking.IGridHost;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;

public class AdapterDrive extends AbstractGridAdapter{
	
	public AdapterDrive(){
		super("appeng.tile.storage.TileDrive");
	}
	
	@Override
	public String getSourceId() {
		return "me_drive";
	}

	@LuaCallable(description = "Returns a list of tables with information concerning all cells in the attached ME drive. If the drive is empty the method returns nil.", returnTypes = LuaReturnType.TABLE)
	public List<Map<String, Object>> getCellInfos(IGridHost gridHost) {
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		
		IInventory inv = (IInventory) gridHost;
		for (int i = 0; i < inv.getSizeInventory();i++) {//loop the inventory and find all cells
			ItemStack itemstack = inv.getStackInSlot(i);
			if(itemstack != null){
				Item item = itemstack.getItem();
				if(item instanceof IStorageCell){
					IMEInventoryHandler inventory = AEApi.instance().registries().cell().getCellInventory( itemstack, null, StorageChannel.ITEMS );
					if (inventory instanceof ICellInventoryHandler){
						ICellInventoryHandler handler = ( ICellInventoryHandler ) inventory;
						ICellInventory cellInventory = handler.getCellInv();

						if ( cellInventory != null ){
							Map<String, Object> luarepr = AEHelper.cellInfoToMap(handler, cellInventory);//fill in the array with cell information
							luarepr.put("name", itemstack.getDisplayName());//add the of the drive, usefull if drive is renamed
							ret.add(luarepr);//add the information to the array				
						}
					}
				}
			}
		}
		
		//if we found cells, we return them, otherwise we return null
		if(ret.size()>0)
			return ret;
		else
			return null;
		
	}
}
