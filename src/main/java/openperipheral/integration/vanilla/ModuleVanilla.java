package openperipheral.integration.vanilla;

import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import openmods.integration.IIntegrationModule;
import openperipheral.api.*;
import openperipheral.integration.Config;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ModuleVanilla implements IIntegrationModule {
	public static String DUMMY_VANILLA_MODID = "vanilla";

	@Override
	public String name() {
		return "Vanilla CC integration module";
	}

	@Override
	public boolean canLoad() {
		return !ArrayUtils.contains(Config.modBlacklist, DUMMY_VANILLA_MODID);
	}

	@Override
	public void load() {
		final IAdapterRegistry adapterRegistry = ApiAccess.getApi(IAdapterRegistry.class);
		adapterRegistry.register(new AdapterInventory());
		adapterRegistry.register(new AdapterNoteBlock());
		adapterRegistry.register(new AdapterComparator());
		adapterRegistry.register(new AdapterBeacon());
		adapterRegistry.register(new AdapterBrewingStand());
		adapterRegistry.register(new AdapterFluidHandler());
		adapterRegistry.register(new AdapterFluidTank());
		adapterRegistry.register(new AdapterFurnace());
		adapterRegistry.register(new AdapterMobSpawner());
		adapterRegistry.register(new AdapterRecordPlayer());
		adapterRegistry.register(new AdapterSign());
		adapterRegistry.register(new AdapterDaylightSensor());

		final IItemStackMetadataBuilder itemMeta = ApiAccess.getApi(IItemStackMetadataBuilder.class);
		itemMeta.register(new EnchantedBookMetaProvider());
		itemMeta.register(new EnchantmentMetaProvider());

		final IEntityMetadataBuilder entityMeta = ApiAccess.getApi(IEntityMetadataBuilder.class);
		entityMeta.register(new EntityBatMetaProvider());
		entityMeta.register(new EntityCreeperMetaProvider());
		entityMeta.register(new EntityHorseMetaProvider());
		entityMeta.register(new EntityLivingMetaProvider());
		entityMeta.register(new EntityPigMetaProvider());
		entityMeta.register(new EntityPlayerMetaProvider());
		entityMeta.register(new EntitySheepMetaProvider());
		entityMeta.register(new EntityTameableMetaProvider());
		entityMeta.register(new EntityVillagerMetaProvider());
		entityMeta.register(new EntityWitchMetaProvider());
		entityMeta.register(new EntityWolfMetaProvider());
		entityMeta.register(new EntityZombieMetaProvider());
	}

	public static Object listEnchantments(NBTTagList ench) {
		List<Map<String, Object>> response = Lists.newArrayList();
		for (int i = 0; i < ench.tagCount(); ++i) {
			NBTTagCompound enchTag = (NBTTagCompound)ench.tagAt(i);
			short id = enchTag.getShort("id");
			short lvl = enchTag.getShort("lvl");

			final Enchantment enchantment = Enchantment.enchantmentsList[id];
			if (enchantment != null) {
				Map<String, Object> entry = Maps.newHashMap();
				entry.put("name", enchantment.getName());
				entry.put("level", lvl);
				entry.put("fullName", enchantment.getTranslatedName(lvl));
				response.add(entry);
			}
		}
		return response;
	}
}