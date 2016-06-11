package openperipheral.integration.vanilla;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import openmods.integration.IIntegrationModule;
import openperipheral.api.adapter.IScriptType;
import openperipheral.integration.OpcAccess;

public class ModuleVanilla implements IIntegrationModule {
	public static String DUMMY_VANILLA_MODID = "vanilla";

	@Override
	public String name() {
		return "Vanilla CC integration module";
	}

	@Override
	public boolean canLoad() {
		return true;
	}

	private static final IScriptType FINGERPRINT_TYPE = new IScriptType() {
		@Override
		public String describe() {
			return "{id:string,dmg:number?,nbt_hash:string?}";
		}
	};

	@Override
	public void load() {
		OpcAccess.adapterRegistry.register(new AdapterNoteBlock());
		OpcAccess.adapterRegistry.register(new AdapterComparator());
		OpcAccess.adapterRegistry.register(new AdapterBeacon());
		OpcAccess.adapterRegistry.register(new AdapterBrewingStand());
		OpcAccess.adapterRegistry.register(new AdapterFluidHandler());
		OpcAccess.adapterRegistry.register(new AdapterFluidTank());
		OpcAccess.adapterRegistry.register(new AdapterFurnace());
		OpcAccess.adapterRegistry.register(new AdapterMobSpawner());
		OpcAccess.adapterRegistry.register(new AdapterRecordPlayer());
		OpcAccess.adapterRegistry.register(new AdapterSign());
		OpcAccess.adapterRegistry.register(new AdapterDaylightSensor());
		OpcAccess.adapterRegistry.register(new AdapterSkull());
		OpcAccess.adapterRegistry.register(new AdapterFlowerPot());

		OpcAccess.itemStackMetaBuilder.register(new EnchantedBookMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new EnchantmentMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new FluidContainerMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new BurnTimeMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new OreDictMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new FingerprintMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new ItemToolClassMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new ItemToolMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new ItemArmorMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new ItemSwordMetaProvider());
		OpcAccess.itemStackMetaBuilder.register(new ItemPotionMetaProvider());

		OpcAccess.entityMetaBuilder.register(new EntityItemMetaProvider());
		OpcAccess.entityMetaBuilder.register(new PaintingMetaProvider());
		OpcAccess.entityMetaBuilder.register(new ItemFrameMetaProvider());

		OpcAccess.entityMetaBuilder.register(new EntityBatMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityCreeperMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityHorseMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityLivingMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityPigMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityPlayerMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntitySheepMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityTameableMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityVillagerMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityWitchMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityWolfMetaProvider());
		OpcAccess.entityMetaBuilder.register(new EntityZombieMetaProvider());
		OpcAccess.entityMetaBuilder.register(new InventoryProviderMetaProvider());

		OpcAccess.converterManager.register(new ConverterItemFingerprint());

		OpcAccess.typeClassifier.registerType(ItemFingerprint.class, FINGERPRINT_TYPE);
	}

	public static Object listEnchantments(NBTTagList ench) {
		List<Map<String, Object>> response = Lists.newArrayList();
		for (int i = 0; i < ench.tagCount(); ++i) {
			NBTTagCompound enchTag = ench.getCompoundTagAt(i);
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
