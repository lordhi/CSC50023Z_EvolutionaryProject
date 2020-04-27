public class TwoPointCrossOver
extends CrossOver
{
	public Genotype[] crossOver(Genotype p1, Genotype p2, GeneticConfiguration config)
	{
		boolean genome1[] = p1.getGenome();
		boolean genome2[] = p2.getGenome();
		int range[] = generateRange(config.genomeLength, config);

		swapBetween(genome1, genome2, range[0], range[1], config);

		Genotype children[] = new Genotype[2];
		children[0] = new Genotype(genome1, config);
		children[1] = new Genotype(genome2, config);

		return children;
	}

	/*Generates two integers less than the value given, with the first larger than the second*/
	private int[] generateRange(int l, GeneticConfiguration config)
	{
		int vals[] = new int[2];

		vals[0] = config.randomGenerator.nextInt(l);
		vals[1] = config.randomGenerator.nextInt(l);

		if (vals[0] > vals[1])
		{
			int tmp = vals[0];
			vals[0] = vals[1];
			vals[1] = tmp;
		}

		return vals;
	}

	public void swapBetween(boolean[] one, boolean[] two,
		int first, int second, Configuration config)
	{
		boolean tmp;
		for (int i=first; i<second; i++)
		{
			tmp = one[i];
			one[i] = two[i];
			two[i] = tmp;
		}
	}

	public String toString()
	{
		return "2PX";
	}
}