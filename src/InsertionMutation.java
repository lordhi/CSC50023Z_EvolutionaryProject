public class InsertionMutation
extends Mutation
{
	public Genotype mutate(Genotype p, GeneticConfiguration config)
	{
		boolean newGenome[] = p.getGenome();

		int pos1 = config.randomGenerator.nextInt(config.genomeLength);
		int pos2 = config.randomGenerator.nextInt(config.genomeLength);

		if (pos1 == pos2)
			return new Genotype(newGenome, config);
		if (pos1 > pos2)
		{
			int tmp = pos1;
			pos1 = pos2;
			pos2 = tmp;
		}

		boolean beingMoved = newGenome[pos2];

		for (int i=pos1+1; i<pos2; i++)
			newGenome[i+1] = newGenome[i];
		newGenome[pos1+1] = beingMoved;

		return new Genotype(newGenome, config);
	}

	public String toString()
	{
		return "ISM";
	}
}