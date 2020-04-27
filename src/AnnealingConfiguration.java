import java.util.HashMap;

public class AnnealingConfiguration
extends Configuration
{
	final double t0;
	final double k;
	final double coolingRate;

	public AnnealingConfiguration(HashMap<String,String> values, String reportFileName, int weightsAndValues[][])
	{
		super(weightsAndValues, reportFileName, values.get("configuration"));

		this.t0 = Double.parseDouble(values.get("initial_temperature"));
		this.coolingRate = Double.parseDouble(values.get("cooling_rate"));
		this.k = 1;
	}

	public AnnealingConfiguration(int t0, double k, double coolingRate,
		String reportFileName, String configFilePath, int weightsAndValues[][])
	{
		super(weightsAndValues, reportFileName, configFilePath);

		this.t0 = t0;
		this.k = k;
		this.coolingRate = coolingRate;
	}

	public String toString()
	{
		return "SA | #" + this.maxGenerations + " | " + t0 + " | " + coolingRate;
	}
}