public class ParticleSwarm
extends Thread
{
	ParticleSwarmConfiguration config;
	Particle swarm[];
	Solution bestCandidates[];

	public ParticleSwarm(ParticleSwarmConfiguration config)
	{
		this.config = config;

		swarm = new Particle[config.swarmSize];
		bestCandidates = new Solution[config.maxGenerations];		
	}

	public void run()
	{
		long startTime = System.currentTimeMillis();
		setup();

		int i=1;
		int hasntChanged = 0;
		while(i < config.maxGenerations)
		{
			for (Particle p : swarm)
				p.updateVelocity();
			for (Particle p : swarm)
				p.updatePosition();

			bestCandidates[i] = config.globalBestKnown.getSolution();
			/*if (bestCandidates[i] == bestCandidates[i-1])
				hasntChanged++;
			else
				hasntChanged = 0;*/
			i++;
		}
		//System.out.println(bestCandidates[config.maxGenerations-1]);
		long runtime = System.currentTimeMillis() - startTime;
		config.generateAndSaveReport(bestCandidates, runtime);
	}

	public void setup()
	{
		for (int i=0; i<config.swarmSize; i++)
			swarm[i] = new Particle(config);
		
		bestCandidates[0] = config.globalBestKnown.getSolution();
	}

	public int getBestValue()
	{
		return bestCandidates[config.maxGenerations-1].getValue();
	}
}