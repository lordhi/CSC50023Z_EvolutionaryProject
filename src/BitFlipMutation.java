public class BitFlipMutation
extends Mutation
{
	public Genotype mutate(Genotype p, GeneticConfiguration config)
	{
		boolean newGenome[] = p.getGenome();
		int index = config.randomGenerator.nextInt(config.genomeLength);
		newGenome[index] = !newGenome[index];

		return new Genotype(newGenome, config);
	}

	public String toString()
	{
		return "BFM";
	}
}