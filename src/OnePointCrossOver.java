public class OnePointCrossOver
extends CrossOver
{
	public Genotype[] crossOver(Genotype p1, Genotype p2, GeneticConfiguration config)
	{
		boolean genome1[] = p1.getGenome();
		boolean genome2[] = p2.getGenome();
		swapPast(genome1, genome2,
			config.randomGenerator.nextInt(config.genomeLength-1), config);

		Genotype children[] = new Genotype[2];
		children[0] = new Genotype(genome1, config);
		children[1] = new Genotype(genome2, config);

		return children;
	}

	private void swapPast(boolean[] one, boolean[] two, int point, GeneticConfiguration config)
	{
		boolean tmp;
		for (int i=point; i<config.genomeLength; i++)
		{
			tmp = one[i];
			one[i] = two[i];
			two[i] = tmp;
		}
	}

	public String toString()
	{
		return "1PX";
	}
}