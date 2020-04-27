public class InterchangeMutation
extends Mutation
{
	public Genotype mutate(Genotype p, GeneticConfiguration config)
	{
		boolean newGenome[] = p.getGenome();

		int pos1 = config.randomGenerator.nextInt(config.genomeLength);
		int pos2 = config.randomGenerator.nextInt(config.genomeLength);

		boolean tmp = newGenome[pos1];
		newGenome[pos1] = newGenome[pos2];
		newGenome[pos2] = tmp;

		return new Genotype(newGenome, config);
	}

	public String toString()
	{
		return "EXM";
	}
}