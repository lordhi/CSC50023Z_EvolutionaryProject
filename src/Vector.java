public class Vector
{
	protected double values[];
	protected static ParticleSwarmConfiguration config;

	public Vector(double values[])
	{
		this.values = values;
	}

	public Vector(double values[], boolean fixVelocity)
	{
		this.values = values;
		if (fixVelocity)
			fixVelocity();
	}

	public Vector(ParticleSwarmConfiguration config)
	{
		this.config = config;
		this.values = new double[config.genomeLength];
		double range = config.maximumVelocity - config.minimumVelocity;

		for (int i=0; i<config.genomeLength; i++)
			values[i] = config.randomGenerator.nextDouble()*range + config.minimumVelocity;	

		fixVelocity();	
	}

	public void fixVelocity()
	{
		double change = 1;
		if (this.getMagnitude() > config.maximumVelocity)
			change = config.maximumVelocity/this.getMagnitude();
		else if (this.getMagnitude() < config.minimumVelocity)
			change = config.minimumVelocity/this.getMagnitude();

		if (change != 1)
			for (int i=0; i<config.genomeLength; i++)
				values[i] *= change;
	}

	public double getMagnitude()
	{
		double mag2 = 0;
		for (double d : values)
			mag2 += d*d;

		return Math.sqrt(mag2);
	}

	public Vector add(Vector other)
	{
		double newVector[] = new double[values.length];
		for (int i=0; i<values.length; i++)
			newVector[i] = values[i] + other.values[i];

		return new Vector(newVector);
	}

	public Vector updateVelocity(Vector one, Vector two, Vector point)
	{
		double newVector[] = new double[values.length];
		double r_1 = config.randomGenerator.nextDouble(true, true);
		double r_2 = config.randomGenerator.nextDouble(true, true);
		for (int i=0; i<values.length; i++)
		{
			newVector[i] = config.inertia*values[i] +
							config.c1*r_1*(one.values[i] - point.values[i]) +
							config.c2*r_2*(two.values[i] - point.values[i]);
		}

		return new Vector(newVector);
	}
}