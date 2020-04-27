import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Configuration
{
	final MersenneTwister randomGenerator;

	int maxGenerations = 10000;
	final int maxYearsWithoutImprovement = 100;

	final int genomeLength = 150;

	final String reportFileName;
	final String configName;
	final double highestValue = 997;
	final int maxWeight = 822;

	final int weights[];
	final int values[];

	public Configuration(int[][] weightsAndValues,
		String reportFileName, String configName)
	{
		this.randomGenerator = new MersenneTwister();

		this.weights = weightsAndValues[0];
		this.values = weightsAndValues[1];

		this.reportFileName = reportFileName;
		this.configName = configName;
	}

	public int[] calculateWeightValue(boolean[] genome)
	{
		int tmp[] = new int[2]; //0 is weights, 1 is values
		tmp[0] = 0;
		tmp[1] = 0;

		for (int i=0; i<genomeLength; i++)
			if (genome[i])
			{
				tmp[0] += weights[i];
				tmp[1] += values[i];
			}

		return tmp;
	}

	public void generateAndSaveReport(Solution[] lines, long runtime)
	{

		try(Writer fileWriter =
			new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(reportFileName), "utf-8"));)
		{			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			fileWriter.write("Evaluation | " + LocalDateTime.now() + "\r\n");
			fileWriter.write("Configuration:\t" + configName + ".json\r\n");
			fileWriter.write("\t\t\t\t" + this + "\r\n");
			fileWriter.write("===============================================\r\n");
			fileWriter.write("#\tbweight\tbvalue\tsquality\tknapsack\r\n");
			fileWriter.write("-----------------------------------------------\r\n");

			Solution best = lines[0];
			for (int i=0; i<lines.length; i++)
			{
				if (lines[i].compareTo(best) < 0)
					best = lines[i];
				fileWriter.write((i+1) + "\t" + best.toString() + "\t" + best.getStringGenome() + "\r\n");
			}
			fileWriter.write("-----------------------------------------------\r\n");
			fileWriter.write("[Statistics]\r\n");
			fileWriter.write("Runtime\t" + runtime + "ms\r\n\r\n");
			fileWriter.write("Convergence\t#\tbweight\tbvalue\tsquality\r\n");
			fileWriter.write("\t\t\t"+(maxGenerations/4)+"\t"+lines[maxGenerations/4-1].toString() + "\r\n");
			fileWriter.write("\t\t\t"+(maxGenerations/2)+"\t"+lines[maxGenerations/2-1].toString() + "\r\n");
			fileWriter.write("\t\t\t"+(maxGenerations*3/4)+"\t"+lines[(maxGenerations*3/4)-1].toString() + "\r\n");
			fileWriter.write("\t\t\t"+maxGenerations+"\t"+lines[maxGenerations-1].toString() + "\r\n");
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public abstract String toString();
}