public class RouletteWheelSelection
extends ParentSelection
{
	public Genotype[][] selectParents(Genotype[] population, GeneticConfiguration config)
	{
		Genotype parents[][] = new Genotype[config.populationSize/2][2];
		double wheel[] = setupRouletteWheel(population);

		for (int i=0; i<parents.length; i++)
			for (int j=0; j<parents[0].length; j++)
				parents[i][j] = rollWheel(population, wheel, config);

		return parents;
	}

	private double[] setupRouletteWheel(Genotype[] population)
	{
		double wheel[] = new double[population.length];

		wheel[0] = population[0].getFitness();
		for (int i=1; i<population.length; i++)
			wheel[i] = wheel[i-1] + population[i].getFitness();

		for (int i=0; i<population.length; i++)
			wheel[i] /= wheel[population.length-1];

		return wheel;
	}

	private Genotype rollWheel(Genotype[] population, double[] rouletteWheel, GeneticConfiguration config)
	{
		double val = config.randomGenerator.nextDouble(true, true);
		int i;
		for (i=0; i<population.length; i++)
			if (val<=rouletteWheel[i])
				return population[i];
		return null;
	}

	public String toString()
	{
		return "RWS";
	}
}