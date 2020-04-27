public class TournamentSelection
extends ParentSelection
{
	int k = 5;
	public Genotype[][] selectParents(Genotype[] population, GeneticConfiguration config)
	{
		Genotype parents[][] = new Genotype[config.populationSize/2][2];

		for (int i=0; i<parents.length; i++)
			for (int j=0; j<parents[0].length; j++)
				parents[i][j] = selectParent(population, config);

		return parents;
	}

	private Genotype selectParent(Genotype[] population, GeneticConfiguration config)
	{
		int bestSelection = 0;
		int newSelection;
		for (int i=0; i<k; i++)
		{
			newSelection = config.randomGenerator.nextInt(population.length);
			if (newSelection < bestSelection)
				bestSelection = newSelection;
		}

		return population[bestSelection];
	}

	public String toString()
	{
		return "TS";
	}
}