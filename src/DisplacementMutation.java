import java.util.LinkedList;

public class DisplacementMutation
extends Mutation
{
	public Genotype mutate(Genotype p, GeneticConfiguration config)
	{
		LinkedList<Boolean> tempGenome = conversion(p.getGenome());

		int pos1 = config.randomGenerator.nextInt(config.genomeLength);
		int pos2 = config.randomGenerator.nextInt(config.genomeLength);

		if (pos1 > pos2)
		{
			int tmp = pos1;
			pos1 = pos2;
			pos2 = tmp;
		}

		LinkedList<Boolean> beingMoved = new LinkedList<>();
		for (int i=0; i<pos2-pos1; i++)
		{
			beingMoved.add(tempGenome.get(pos1));
			tempGenome.remove(pos1);
		}

		int moveToIndex = config.randomGenerator.nextInt(tempGenome.size());
		for (int i=moveToIndex; i<moveToIndex + pos2-pos1; i++)
			tempGenome.add(i, beingMoved.poll());

		boolean newGenome[] = conversionBack(tempGenome);

		return new Genotype(newGenome, config);
	}

	public boolean[] conversionBack(LinkedList<Boolean> bl)
	{
		boolean arr[] = new boolean[bl.size()];
		for (int i=0; i<arr.length; i++)
			arr[i] = bl.poll();
		return arr;
	}

	public LinkedList<Boolean> conversion(boolean arr[])
	{
		LinkedList<Boolean> capitalGenome = new LinkedList<>();
		for (int i=0; i<arr.length; i++)
			capitalGenome.add(Boolean.valueOf(arr[i]));
		return capitalGenome;
	}

	public String toString()
	{
		return "DPM";
	}
}