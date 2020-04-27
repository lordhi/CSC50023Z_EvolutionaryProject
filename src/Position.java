public class Position
extends Vector
implements Comparable<Position>
{
	private Solution equivalentSolution;

	public Position(double values[])
	{
		super(new double[values.length]);
		for (int i=0; i<values.length; i++)
			if (values[i] > 1)
				this.values[i] = 1;
			else if (values[i] < 0)
				this.values[i] = 0;
			else
				this.values[i] = values[i];

		boolean solution[] = new boolean[values.length];

		for (int i=0; i<values.length; i++)
			if (values[i] > 0.5)
				solution[i] = true;
			else
				solution[i] = false;

		equivalentSolution = new Solution(solution, config);
	}

	public Position(ParticleSwarmConfiguration config)
	{
		super(new double[config.genomeLength]);
		this.config = config;

		equivalentSolution = new Solution(config);
		boolean[] position = equivalentSolution.getGenome();

		for (int i=0; i<config.genomeLength; i++)
			if (position[i])
				this.values[i] = 1;
			else
				this.values[i] = 0;
	}

	public Position add(Vector other)
	{
		double newVector[] = new double[values.length];
		for (int i=0; i<values.length; i++)
			newVector[i] = values[i] + other.values[i];

		return new Position(newVector);
	}

	public Solution getSolution()
	{
		return equivalentSolution;
	}

	public double getFitness()
	{
		return equivalentSolution.getFitness();
	}

	public int compareTo(Position other)
	{
		return equivalentSolution.compareTo(other.equivalentSolution);
	}
}