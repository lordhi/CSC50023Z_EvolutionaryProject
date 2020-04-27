import java.io.File;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import java.io.IOException;

public class Control
{
	public static void main(String args[])
	throws Exception
	{
		int knapsackInstance[][] = readKnapsack("./Data/knapsack_instance.csv");

		for (int i=0; i<args.length-1; i++)
		{
			if (args[i].equals("-configuration"))
				runConfigFile(args[i+1]);
			if (args[i].equals("-search_best_configuration"))
				runSelectedAndSave(args[i+1]);
		}

		//runGeneticConfigs("./Configurations/Genetic_Algorithms/", knapsackInstance);
		//runParticleConfigs("./Configurations/Particle_Swarm/", knapsackInstance);
		//runAnnealingConfigs("./Configurations/Simulated_Annealing/", knapsackInstance);
	}

	private static void runSelectedAndSave(String type)
	throws Exception
	{
		int knapsackInstance[][] = readKnapsack("./Data/knapsack_instance.csv");
		File bestConfig = null;

		if (type.equals("ga"))
			bestConfig = runGeneticConfigs("./Configurations/Genetic_Algorithms/", knapsackInstance);
		else if (type.equals("sa"))
			bestConfig =  runAnnealingConfigs("./Configurations/Simulated_Annealing/", knapsackInstance);
		else if (type.equals("pso"))
			bestConfig =  runParticleConfigs("./Configurations/Particle_Swarm/", knapsackInstance);
		else
			throw new Exception(type + " does not exist. Please select from 'ga', 'sa' or 'pso'");

		String saveTo = "../Configurations/" + type + "_best.json";
		readAndWrite(bestConfig, saveTo);
	}

	private static void readAndWrite(File read, String write)
	throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(read));
		Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(write), "utf-8"));

		String line;
		while ((line = br.readLine()) != null)
			fw.write(line + "\r\n");

		br.close();
		fw.close();
	}

	private static void runConfigFile(String filename)
	throws Exception
	{
		String type = filename.substring(0,2);
		int knapsackInstance[][] = readKnapsack("./Data/knapsack_instance.csv");

		if (type.equals("ga"))
			runGeneticConfig(new File("./Configurations/Genetic_Algorithms/" + filename), knapsackInstance);
		else if (type.equals("sa"))
			runAnnealingConfig(new File("./Configurations/Simulated_Annealing/" + filename), knapsackInstance);
		else if (type.equals("ps"))
			runParticleConfig(new File("./Configurations/Particle_Swarm/" + filename), knapsackInstance);
		else if (filename.equals("best"))
		{
			runGeneticConfig(new File("../Configurations/ga_best.json"), knapsackInstance);
			runAnnealingConfig(new File("../Configurations/sa_best.json"), knapsackInstance);
			runParticleConfig(new File("../Configurations/pso_best.json"), knapsackInstance);
		}
	}

	private static File runAnnealingConfigs(String filename, int knapsackInstance[][])
	throws Exception
	{
		File annealingConfigFolder = new File(filename);
		File bestConfig = null;
		SimulatedAnnealing bestAnnealing = null;


		for (File annealingConfigFile : annealingConfigFolder.listFiles())
		{	
			SimulatedAnnealing sa = runAnnealingConfig(annealingConfigFile, knapsackInstance);
			
			if (bestAnnealing == null || sa.getBestValue() > bestAnnealing.getBestValue())
			{
				bestConfig = annealingConfigFile;
				bestAnnealing = sa;
			}
		}

		return bestConfig;
	}

	private static SimulatedAnnealing runAnnealingConfig(File annealingConfigFile, int knapsackInstance[][])
	throws Exception
	{
		AnnealingConfiguration ac = new AnnealingConfiguration(
			JsonReader.read(annealingConfigFile.getAbsolutePath()),
			"./Results/" + annealingConfigFile.getName().substring(0,annealingConfigFile.getName().length()-5) + ".txt",
			knapsackInstance);

		SimulatedAnnealing sa = new SimulatedAnnealing(ac);
		sa.run();

		return sa;
	}

	private static File runParticleConfigs(String filename, int knapsackInstance[][])
	throws Exception
	{
		File particleConfigFolder = new File(filename);
		File bestConfig = null;
		ParticleSwarm bestSwarm = null;

		for (File particleConfigFile : particleConfigFolder.listFiles())
		{
			ParticleSwarm pso = runParticleConfig(particleConfigFile, knapsackInstance);

			if (bestSwarm == null || pso.getBestValue() > bestSwarm.getBestValue())
			{
				bestConfig = particleConfigFile;
				bestSwarm = pso;
			}
		}

		return bestConfig;
	}

	private static ParticleSwarm runParticleConfig(File particleConfigFile, int knapsackInstance[][])
	throws Exception
	{
		ParticleSwarmConfiguration psc = new ParticleSwarmConfiguration(
			JsonReader.read(particleConfigFile.getAbsolutePath()),
			"./Results/" + particleConfigFile.getName().substring(0,particleConfigFile.getName().length()-5) + ".txt",
			knapsackInstance);

		ParticleSwarm pso = new ParticleSwarm(psc);
		pso.run();
		return pso;
	}

	private static File runGeneticConfigs(String filename, int knapsackInstance[][])
	throws Exception
	{
		File geneticConfigFolder = new File(filename);
		File bestConfig = null;
		GeneticAlgorithm bestGenetic = null;

		for (File geneticConfigFile : geneticConfigFolder.listFiles())
		{
			GeneticAlgorithm ga = runGeneticConfig(geneticConfigFile, knapsackInstance);

			if (bestGenetic == null || ga.getBestValue() > bestGenetic.getBestValue())
			{
				bestConfig = geneticConfigFile;
				bestGenetic= ga;
			}
		}

		return bestConfig;
	}

	private static GeneticAlgorithm runGeneticConfig(File geneticConfigFile, int knapsackInstance[][])
	throws Exception
	{
		GeneticConfiguration cg = new GeneticConfiguration(
			JsonReader.read(geneticConfigFile.getAbsolutePath()),
			"./Results/" + geneticConfigFile.getName().substring(0,geneticConfigFile.getName().length()-5) + ".txt",
			knapsackInstance);

		GeneticAlgorithm ga = new GeneticAlgorithm(cg);
		ga.run();
		return ga;
	}

	private static int[][] readKnapsack(String filename)
	{
		int result[][] = new int[2][150];
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			reader.readLine();

			String st;
			String vals[];
			int i = 0;
			while((st = reader.readLine()) != null)
			{
				vals = st.split(";");
				result[0][i] = Integer.parseInt(vals[1]);
				result[1][i] = Integer.parseInt(vals[2]);
				i += 1;
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}