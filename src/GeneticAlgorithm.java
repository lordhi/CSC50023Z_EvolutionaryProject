import java.util.Arrays;

public class GeneticAlgorithm
extends Thread
{
	private Genotype[] bestCandidates;
	private Genotype[] population;
	private GeneticConfiguration config;

	private int numberOfCrossoverOperations;
	private int numberOfMutationOperations;

	private int yearsSinceImprovement;
	private int bestValue;

	public GeneticAlgorithm(GeneticConfiguration config)
	{
		this.config = config;
	}

	public void run()
	{
		long startTime = System.currentTimeMillis();
		setup();

		for (int i=1; i<config.maxGenerations; i++)
		{
			tick();
			bestCandidates[i] = population[0];

			if (bestCandidates[i].getValue() <= bestValue)
			{
				yearsSinceImprovement++;
				if (yearsSinceImprovement >= config.maxYearsWithoutImprovement)
				{
					cutGenerations(i-50);
				}
			}
			else{
				yearsSinceImprovement = 0;
				bestValue = bestCandidates[i].getValue();
			}
		}

		long runtime = System.currentTimeMillis() - startTime;
		//System.out.println(runtime);
		config.generateAndSaveReport(bestCandidates, runtime);
		//System.out.println(bestCandidates[bestCandidates.length-1]);
	}

	private void setup()
	{
		numberOfMutationOperations = 0;
		numberOfCrossoverOperations = 0;

		bestCandidates = new Genotype[config.maxGenerations];
		population = new Genotype[config.populationSize];

		for (int i=0; i<config.populationSize; i++)
			population[i] = new Genotype(config);

		Arrays.sort(population);
		bestCandidates[0] = population[0];
		bestValue = bestCandidates[0].getValue();
		yearsSinceImprovement = 0;
	}

	private void tick()
	{
		Genotype parents[][] = config.parentSelection.selectParents(population, config);
		generateNewGeneration(parents);
		mutateGeneration();
	}

	private void cutGenerations(int i)
	{
		config.maxGenerations = i;

		Genotype newBestCandidates[] = new Genotype[i];
		for (int j=0; j<i; j++)
			newBestCandidates[j] = bestCandidates[j];

		bestCandidates = newBestCandidates;
	}

	private void generateNewGeneration(Genotype[][] parents)
	{
		Genotype tmp[];	//Allows us to take the return from children generation without allocating new memory each time

		for (int i=0; i<parents.length; i++)
		{
			if (config.randomGenerator.nextDouble(true, true) < config.crossoverChance)
			{
				tmp = config.crossover.crossOver(parents[i][0], parents[i][1], config);
				numberOfCrossoverOperations++;
				population[i*2] = tmp[0];
				population[i*2+1] = tmp[1];
			}else{
				population[i*2] = parents[i][0];
				population[i*2+1] = parents[i][1];
			}
		}
	}

	private void mutateGeneration()
	{
		for (int i=0; i<population.length; i++)
		{
			if (config.randomGenerator.nextDouble(true, true) < config.mutationChance)
			{
				numberOfMutationOperations += 1;
				population[i] = config.mutation.mutate(population[i], config);
			}
		}
		Arrays.sort(population);
	}

	public int getBestValue()
	{
		return bestCandidates[bestCandidates.length-1].getValue();
	}
}