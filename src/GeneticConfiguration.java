import java.util.HashMap;

public class GeneticConfiguration
extends Configuration
{
	final int populationSize = 2048;

	final double crossoverChance;
	final double mutationChance;

	final Mutation mutation;
	final CrossOver crossover;
	final ParentSelection parentSelection;

	public GeneticConfiguration(HashMap<String,String> values, String reportFileName, int weightsAndValues[][])
	throws Exception
	{
		super(weightsAndValues, reportFileName, values.get("configuration"));
		this.crossoverChance = Double.parseDouble(values.get("crossover_ratio"));
		this.mutationChance = Double.parseDouble(values.get("mutation_ratio"));

		this.mutation = parseMutation(values.get("mutation_method"));
		this.crossover = parseCrossover(values.get("crossover_method"));
		this.parentSelection = parseSelection(values.get("selection_method"));
	}

	public GeneticConfiguration(double crossoverChance,
		String reportFileName, String configName,
		double mutationChance, int weightsAndValues[][],
		Mutation mutation, CrossOver crossover, ParentSelection parentSelection)
	{
		super(weightsAndValues, reportFileName, configName);

		this.crossoverChance = crossoverChance;
		this.mutationChance = mutationChance;
		
		this.mutation = mutation;
		this.crossover = crossover;
		this.parentSelection = parentSelection;
	}

	public String toString()
	{
		return "GA | #" + this.maxGenerations + " | " + parentSelection + " | " + crossover + "(" + crossoverChance + ") | " + mutation + " (" + mutationChance + ")";
	}

	private Mutation parseMutation(String name)
	throws Exception
	{
		name = name.toUpperCase();
		switch(name)
		{
			case "BFM":
				return new BitFlipMutation();
			case "EXM":
				return new InterchangeMutation();
			case "IVM":
				return new InversionMutation();
			case "ISM":
				return new InsertionMutation();
			case "DPM":
				return new DisplacementMutation();
			default:
				throw new Exception("Mutation '" + name + "' was not found.");
		}
	}

	private CrossOver parseCrossover(String name)
	throws Exception
	{
		name = name.toUpperCase();
		switch(name)
		{
			case "1PX":
				return new OnePointCrossOver();
			case "2PX":
				return new TwoPointCrossOver();
			default:
				throw new Exception("Crossover '" + name + "' was not found.");
		}
	}

	private ParentSelection parseSelection(String name)
	throws Exception
	{
		name = name.toUpperCase();
		switch(name)
		{
			case "RWS":
				return new RouletteWheelSelection();
			case "TS":
				return new TournamentSelection();
			default:
				throw new Exception("Parent selection method '" + name + "' was not found.");
		}
	}
}