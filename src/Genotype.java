import java.util.Arrays;

public class Genotype
extends Solution
{
	public Genotype(GeneticConfiguration config)
	{
		super(config);
	}

	public Genotype(boolean genome[], GeneticConfiguration config)
	{
		super(genome, config);
	}
}