public class SimulatedAnnealing
extends Thread
{
	AnnealingConfiguration config;
	private Solution[] iterations;
	private Solution best;
	private int worseSolutionsAccepted;

	private int yearsSinceImprovement;

	public SimulatedAnnealing(AnnealingConfiguration config)
	{
		this.config = config;
	}

	public void run()
	{
		long startTime = System.currentTimeMillis();
		setup();
		double temperature = config.t0;

		int i;
		for(i=0; i<config.maxGenerations-1; i++)
		{
			tick(i, temperature);

			temperature = temperature*config.coolingRate;
			if (temperature < 1)
			{
				temperature=1.0;
				break;
			}
		}

		for(;i<config.maxGenerations-1; i++)
			tick(i, temperature);

		long runtime = System.currentTimeMillis() - startTime;
		System.out.println(iterations[iterations.length-1]);
		//config.generateAndSaveReport(iterations, runtime);
	}

	private void tick(int i, double temperature)
	{
		Solution newSol = mutate(iterations[i]);
		if (newSol.compareTo(iterations[i]) < 0)
			iterations[i+1] = newSol;
		else
			if(boltzmannAcceptanceFunction(temperature, iterations[i], newSol))
			{
				iterations[i+1] = newSol;
				worseSolutionsAccepted++;
			}else
				iterations[i+1] = iterations[i];
		if (newSol.compareTo(best) < 0)
		{
			best = newSol;
			yearsSinceImprovement = 0;
		}else
		{
			yearsSinceImprovement += 1;
			if (yearsSinceImprovement > config.maxYearsWithoutImprovement)
			{
				cutGenerations(i);

			}
		}

	}

	private void cutGenerations(int i)
	{
		config.maxGenerations = i;

		Solution newIterations[] = new Solution[i];
		for (int j=0; j<i; j++)
			newIterations[j] = iterations[j];

		iterations = newIterations;
	}

	private boolean boltzmannAcceptanceFunction(double temperature, Solution oldSol, Solution newSol)
	{
		double delta = newSol.getValue() - oldSol.getValue();
		double r = config.randomGenerator.nextDouble(true,true);
		return r < Math.exp(delta/(config.k*temperature));
	}

	private void setup()
	{
		worseSolutionsAccepted = 0;
		yearsSinceImprovement = 0;

		iterations = new Solution[config.maxGenerations];
		iterations[0] = new Solution(config);
		best = iterations[0];
	}

	private Solution mutate(Solution s)
	{
		boolean newGenome[] = s.getGenome();
		int index = config.randomGenerator.nextInt(config.genomeLength);
		newGenome[index] = !newGenome[index];

		return new Solution(newGenome, config);
	}

	public int getBestValue()
	{
		return best.getValue();
	}
}