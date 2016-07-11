package openperipheral.integration.forestry;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.ItemStack;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import openperipheral.integration.Config;

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
	public List<Map<String, Object>> getBeeBreedingData(IBeeHousing housing) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;

		List<Map<String, Object>> result = Lists.newArrayList();

		for (IMutation mutation : beeRoot.getMutations(false)) {
			if (mutation.isSecret() && !Config.showHiddenMutations) continue;
			final Map<String, Object> mutationMap = Maps.newHashMap();
			try {
				IAlleleSpecies allele1 = mutation.getAllele0();
				if (allele1 != null) mutationMap.put(ALLELE_1, allele1.getName());
				IAlleleSpecies allele2 = mutation.getAllele1();
				if (allele2 != null) mutationMap.put(ALLELE_2, allele2.getName());

				final IAlleleSpecies offspringSpecies = getOffspringSpecies(mutation);
				mutationMap.put(MUTATION_RESULT, offspringSpecies.getName());

				mutationMap.put(MUTATION_CHANCE, mutation.getBaseChance());
				mutationMap.put(MUTATION_CONDITIONS, mutation.getSpecialConditions());

				result.add(mutationMap);
			} catch (Exception e) {
				throw new RuntimeException(String.format("Failed to get bee breeding information from %s, collected data: %s", mutation, mutationMap), e);
			}
		}
		return result;
	}

	final Comparator<IAllele> alleleCompatator = Ordering.natural().onResultOf(new Function<IAllele, String>() {
		@Override
		public String apply(IAllele input) {
			return input.getUID();
		}
	});

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get all known bees species")
	public List<Map<String, String>> listAllSpecies(IBeeHousing housing) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;

		final Set<IAlleleSpecies> allSpecies = Sets.newTreeSet(alleleCompatator);

		// approach 1: parents and children of all mutations
		for (IMutation mutation : beeRoot.getMutations(false)) {
			allSpecies.add(mutation.getAllele0());
			allSpecies.add(mutation.getAllele1());
			allSpecies.add(getOffspringSpecies(mutation));
		}

		// approach 2: template bees
		for (IIndividual individual : beeRoot.getIndividualTemplates()) {
			final IGenome genome = individual.getGenome();
			allSpecies.add(genome.getPrimary()); // secondary is same as primary
		}

		// TODO approach 3
		// beeRoot.getRegisteredAlleles(EnumBeeChromosome.SPECIES) (Forestry 4.2 API)

		final List<Map<String, String>> result = Lists.newArrayList();

		for (IAlleleSpecies allele : allSpecies)
			if (!allele.isSecret() || Config.showHiddenBees) result.add(serializeAllele(allele));

		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get possible mutations that results in given bee")
	public List<Map<String, Object>> getBeeParents(IBeeHousing housing, @Arg(name = "childType", description = "The type of bee you want the parents for") String childType) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;

		List<Map<String, Object>> result = Lists.newArrayList();
		childType = childType.toLowerCase(Locale.ENGLISH);

		for (IMutation mutation : beeRoot.getMutations(false)) {
			if (mutation.isSecret() && !Config.showHiddenMutations) continue;
			final IAlleleSpecies species = getOffspringSpecies(mutation);

			if (alleleNameMatches(species, childType)) {
				result.add(serializeMutation(mutation, false));
			}
		}
		return result;
	}

	@ScriptCallable(returnTypes = ReturnType.TABLE, description = "Get possible mutations that can be created with given bee")
	public List<Map<String, Object>> getBeeChildren(IBeeHousing housing, @Arg(name = "parentYpe", description = "The type of bee you want the children for") String childType) {
		ISpeciesRoot beeRoot = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		if (beeRoot == null) return null;

		List<Map<String, Object>> result = Lists.newArrayList();
		childType = childType.toLowerCase(Locale.ENGLISH);

		for (IMutation mutation : beeRoot.getMutations(false)) {
			if (mutation.isSecret() && !Config.showHiddenMutations) continue;

			if (alleleNameMatches(mutation.getAllele0(), childType) || alleleNameMatches(mutation.getAllele1(), childType)) {
				result.add(serializeMutation(mutation, true));
			}
		}
		return result;
	}

	private static boolean alleleNameMatches(IAlleleSpecies species, String childType) {
		final String uid = species.getUID().toLowerCase(Locale.ENGLISH);
		final String localizedName = species.getName().toLowerCase(Locale.ENGLISH);
		return localizedName.equals(childType) || uid.equals(childType);
	}

	private static IAlleleSpecies getOffspringSpecies(IMutation mutation) {
		final IGenome offspringGenome = mutation.getRoot().templateAsGenome(mutation.getTemplate());
		return (IAlleleSpecies)offspringGenome.getActiveAllele(EnumBeeChromosome.SPECIES);
	}

	private static Map<String, String> serializeAllele(IAllele species) {
		Map<String, String> result = Maps.newHashMap();
		result.put("name", species.getName());
		result.put("uid", species.getUID());
		return result;
	}

	private static Map<String, Object> serializeMutation(IMutation mutation, boolean addOffspring) {
		Map<String, Object> parentMap = Maps.newHashMap();

		IAlleleSpecies allele1 = mutation.getAllele0();
		if (allele1 != null) parentMap.put(ALLELE_1, serializeAllele(allele1));

		IAlleleSpecies allele2 = mutation.getAllele1();
		if (allele2 != null) parentMap.put(ALLELE_2, serializeAllele(allele2));

		if (addOffspring) {
			final IAlleleSpecies offspringSpecies = getOffspringSpecies(mutation);
			parentMap.put(MUTATION_RESULT, serializeAllele(offspringSpecies));
		}

		parentMap.put(MUTATION_CHANCE, mutation.getBaseChance());
		parentMap.put(MUTATION_CONDITIONS, mutation.getSpecialConditions());
		return parentMap;
	}

}
