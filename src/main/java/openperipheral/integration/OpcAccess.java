package openperipheral.integration;

import openperipheral.api.ApiHolder;
import openperipheral.api.adapter.IPeripheralAdapterRegistry;
import openperipheral.api.adapter.ITypeClassifier;
import openperipheral.api.converter.IConverterManager;
import openperipheral.api.meta.IEntityPartialMetaBuilder;
import openperipheral.api.meta.IItemStackPartialMetaBuilder;

import com.google.common.base.Preconditions;

public class OpcAccess {

	@ApiHolder
	public static IPeripheralAdapterRegistry adapterRegistry;

	@ApiHolder
	public static IItemStackPartialMetaBuilder itemStackMetaBuilder;

	@ApiHolder
	public static IEntityPartialMetaBuilder entityMetaBuilder;

	@ApiHolder
	public static IConverterManager converterManager;

	@ApiHolder
	public static ITypeClassifier typeClassifier;

	public static void checkApiPresent() {
		Preconditions.checkState(adapterRegistry != null, "Adapter Registry not present");
		Preconditions.checkState(itemStackMetaBuilder != null, "Item stack metadata provider not present");
		Preconditions.checkState(entityMetaBuilder != null, "Entity metadata provider not present");
		Preconditions.checkState(converterManager != null, "Converter manager provider not present");
		Preconditions.checkState(typeClassifier != null, "Type classifier not present");
	}

}
