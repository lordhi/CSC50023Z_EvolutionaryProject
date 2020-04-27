import java.util.HashMap;

public class ParticleSwarmConfiguration
extends Configuration
{
	final int swarmSize;
	final double maximumVelocity;
	final double minimumVelocity;

	final double inertia;
	final double c1;
	final double c2;

	Position globalBestKnown;

	public ParticleSwarmConfiguration(HashMap<String,String> values, String reportFileName, int weightsAndValues[][])
	throws Exception
	{
		super(weightsAndValues, reportFileName, values.get("configuration"));

		this.swarmSize = Integer.parseInt(values.get("number_particles"));
		this.maximumVelocity = Double.parseDouble(values.get("maximum_velocity"));
		this.minimumVelocity = Double.parseDouble(values.get("minimum_velocity"));
		this.inertia = Double.parseDouble(values.get("inertia"));
		this.c1 = Double.parseDouble(values.get("c1"));
		this.c2 = Double.parseDouble(values.get("c2"));
	}

	public String toString()
	{
		return "PSO | #" + this.maxGenerations + " | " + this.swarmSize + " | " + this.minimumVelocity + " | " + this.maximumVelocity + " | " + this.c1 + " | " + this.c2 + " | " + this.inertia;
	}
}