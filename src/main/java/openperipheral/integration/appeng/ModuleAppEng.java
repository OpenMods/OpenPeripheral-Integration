package openperipheral.integration.appeng;

import openmods.Mods;
import openperipheral.api.ApiAccess;
import openperipheral.api.IAdapterRegistry;
import openperipheral.api.ITypeConvertersRegistry;
import openperipheral.integration.OPIntegrationModule;

public class ModuleAppEng extends OPIntegrationModule {

	@Override
	public String getModId() {
		return Mods.APPLIEDENERGISTICS;
	}

	@Override
	public void load() {
		IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterCellProvider());
		adapterRegistry.register(new AdapterGridTileEntity());
		adapterRegistry.register(new AdapterTileController());

		ITypeConvertersRegistry typeRegistry = ApiAccess.getApi(ITypeConvertersRegistry.class);
		typeRegistry.register(new ConverterIItemList());
	}
}
