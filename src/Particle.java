public class Particle
{
	private Vector velocity;
	private Position position;
	private Position bestKnown;

	private ParticleSwarmConfiguration config;

	public Particle(ParticleSwarmConfiguration config)
	{
		this.config = config;

		this.position = new Position(config);
		this.velocity = new Vector(config);
		this.bestKnown = this.position;

		if (config.globalBestKnown == null || this.position.compareTo(config.globalBestKnown) < 0)
			config.globalBestKnown = this.position;
	}

	public void updateVelocity()
	{
		velocity = velocity.updateVelocity(bestKnown, config.globalBestKnown, position);
	}

	public void updatePosition()
	{
		position = position.add(velocity);

		if (this.position.compareTo(config.globalBestKnown) < 0)
			config.globalBestKnown = this.position;
	}
}