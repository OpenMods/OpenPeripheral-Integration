package openperipheral.integration.forestry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.item.ItemStack;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;

public class AdapterBeeHousing implements IPeripheralAdapter {

	private static final String MUTATION_RESULT = "result";
	private static final String MUTATION_CONDITIONS = "specialConditions";
	private static final String MUTATION_CHANCE = "chance";
	private static final String ALLELE_2 = "allele2";
	private static final String ALLELE_1 = "allele1";

	@Override
	public Class<?> getTargetClass() {
		return IBeeHousing.class;
	}

	@Override
	public String getSourceId() {
		return "forestry_bees";
	}

	@ScriptCallable(returnTypes = ReturnType.BOOLEAN, description = "Can the bees breed?")
	public boolean canBreed(IBeeHousing beeHousing) {
		return beeHousing.getBeekeepingLogic().canWork();
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Breeding progress (in %)")
	public int breedingProgress(IBeeHousing beeHousing) {
		return beeHousing.getBeekeepingLogic().getBeeProgressPercent();
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the drone")
	public IIndividual getDrone(IBeeHousing beeHousing) {
		ItemStack drone = beeHousing.getBeeInventory().getDrone();
		return (drone != null)? AlleleManager.alleleRegistry.getIndividual(drone) : null;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the queen")
	public IIndividual getQueen(IBeeHousing beeHousing) {
		ItemStack queen = beeHousing.getBeeInventory().getQueen();
		return (queen != null)? AlleleManager.alleleRegistry.getIndividual(queen) : null;
	}

	@Asynchronous
	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the full breeding list thingy. Experimental!")
	public List<Map<String, Object>> getBeeBreedingData(IBeeHousing housing, @Optionals @Arg(name = "showSecrets", type = ArgType.BOOLEAN) Boolean showSecrets) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;
		if (showSecrets == null) showSecrets = false;

		List<Map<String, Object>> result = Lists.newArrayList();

		for (IMutation mutation : beeRoot.getMutations(false)) {
			if (mutation.isSecret() && !showSecrets) continue;
			final Map<String, Object> mutationMap = Maps.newHashMap();
			try {
				IAlleleSpecies allele1 = mutation.getAllele0();
				if (allele1 != null) mutationMap.put(ALLELE_1, allele1.getName());
				IAlleleSpecies allele2 = mutation.getAllele1();
				if (allele2 != null) mutationMap.put(ALLELE_2, allele2.getName());

				for (IAllele allele : mutation.getTemplate()) {
					if (allele instanceof IAlleleSpecies) {
						mutationMap.put(MUTATION_RESULT, allele.getName());
					}
				}

				mutationMap.put(MUTATION_CHANCE, mutation.getBaseChance());
				mutationMap.put(MUTATION_CONDITIONS, mutation.getSpecialConditions());

				result.add(mutationMap);
			} catch (Exception e) {
				throw new RuntimeException(String.format("Failed to get bee breeding information from %s, collected data: %s", mutation, mutationMap), e);
			}
		}
		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get all known bee species")
	public List<Map<String, String>> listAllSpecies(IBeeHousing housing) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;
		List<Map<String, String>> result = Lists.newArrayList();
		Map<String, String> speciesSerialized;

		for (IMutation mutation : beeRoot.getMutations(false)) {
			speciesSerialized = serializeSpecies(mutation.getAllele0());
			if (!result.contains(speciesSerialized)) {
				result.add(speciesSerialized);
			}
			speciesSerialized = serializeSpecies(mutation.getAllele1());
			if (!result.contains(speciesSerialized)) {
				result.add(speciesSerialized);
			}
		}

		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get the parents for a particular mutation")
	public List<Map<String, Object>> getBeeParents(IBeeHousing housing,
			@Arg(name = "childType", description = "The type of bee you want the parents for") String childType) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;
		List<Map<String, Object>> result = Lists.newArrayList();
		childType = childType.toLowerCase(Locale.ENGLISH);

		for (IMutation mutation : beeRoot.getMutations(false)) {
			IAllele[] template = mutation.getTemplate();
			if (template == null || template.length < 1) continue;

			IAllele allele = template[0];

			if (!(allele instanceof IAlleleSpecies)) continue;

			IAlleleSpecies species = (IAlleleSpecies)allele;
			final String uid = species.getUID().toLowerCase(Locale.ENGLISH);
			final String localizedName = species.getName().toLowerCase(Locale.ENGLISH);

			if (localizedName.equals(childType) || uid.equals(childType)) {
				Map<String, Object> parentMap = serializeMutation(mutation);
				result.add(parentMap);
			}
		}
		return result;
	}

	private static Map<String, String> serializeSpecies(IAlleleSpecies species) {
		Map<String, String> result = Maps.newHashMap();
		result.put("name", species.getName());
		result.put("uid", species.getUID());
		return result;
	}

	private static Map<String, Object> serializeMutation(IMutation mutation) {
		Map<String, Object> parentMap = Maps.newHashMap();

		IAllele allele1 = mutation.getAllele0();
		if (allele1 instanceof IAlleleSpecies) parentMap.put(ALLELE_1, serializeSpecies((IAlleleSpecies)allele1));

		IAllele allele2 = mutation.getAllele1();
		if (allele2 instanceof IAlleleSpecies) parentMap.put(ALLELE_2, serializeSpecies((IAlleleSpecies)allele2));

		parentMap.put(MUTATION_CHANCE, mutation.getBaseChance());
		parentMap.put(MUTATION_CONDITIONS, mutation.getSpecialConditions());
		return parentMap;
	}

}
