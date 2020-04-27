import java.util.Arrays;

public class Solution
implements Comparable<Solution>
{
	private final double fitness;
	private final int weightValue[]; //Holds the weight and value as an int pair
	private final boolean genome[];
	private final Configuration config;

	public Solution(Configuration config)
	{
		this.config = config;
		boolean tempGenome[] = new boolean[config.genomeLength];
		
		for (int i=1; i<config.genomeLength; i++)
			tempGenome[i] = config.randomGenerator.nextBoolean();

		// Makes sure that all starting configs are valid solutions
		makeValid(tempGenome);

		this.genome = tempGenome;
		this.weightValue = config.calculateWeightValue(genome);
		this.fitness = calculateFitness(weightValue, config);
	}

	public Solution(boolean genome[], Configuration config)
	{
		this.config = config;

		makeValid(genome);
		this.genome = genome;

		this.weightValue = config.calculateWeightValue(genome);
		this.fitness = calculateFitness(weightValue, config);
	}

	public void makeValid(boolean genome[])
	{
		while(config.calculateWeightValue(genome)[0] > config.maxWeight)
			genome[config.randomGenerator.nextInt(genome.length)] = false;
	}

	public double calculateFitness(int weightValue[], Configuration config)
	{
		if (weightValue[0] <= config.maxWeight)
			return weightValue[1]/config.highestValue;
		else
			return 0.00001;
	}

	public int getValue()
	{
		return weightValue[1];
	}

	public boolean[] getGenome()
	{
		return Arrays.copyOf(genome, genome.length);
	}

	public double getFitness()
	{
		return fitness;
	}

	@Override
	public String toString()
	{
		if (weightValue[1] > 1000)
			return weightValue[0] + "\t\t" + weightValue[1] + "\t"
				+ String.format("%.3g", fitness*100) + "%"; 
		else
			return weightValue[0] + "\t\t" + weightValue[1] + "\t\t"
				+ String.format("%.3g", fitness*100) + "%"; 
	}

	public String getStringGenome()
	{
		StringBuilder genomeString = new StringBuilder(genome.length+2);
		genomeString.append('[');
		for (int i=0; i<genome.length; i++)
			genomeString.append(genome[i] ? 1 : 0);
		genomeString.append(']');
		return genomeString.toString();
	}

	public int compareTo(Solution other)
	{
		if (fitness < other.fitness)
			return 1;
		if (fitness > other.fitness)
			return -1;
		return 0;
	}
}