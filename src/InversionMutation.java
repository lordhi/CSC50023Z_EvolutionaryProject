public class InversionMutation
extends Mutation
{
	public Genotype mutate(Genotype p, GeneticConfiguration config)
	{
		int range[] = generateRange(config.genomeLength, config);

		boolean newGenome[] = p.getGenome();
		invertRange(newGenome, range[0], range[1]);

		return new Genotype(newGenome, config);
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

	private void invertRange(boolean[] genome, int low, int high)
	{
		int range = high - low;
		int mid = (low+high)/2;

		for (int i=0; i<range; i++)
		{
			boolean tmp = genome[low+i];
			genome[low+i] = genome[high-i];
			genome[high-i] = tmp;
		}
	}

	public String toString()
	{
		return "IVM";
	}
}