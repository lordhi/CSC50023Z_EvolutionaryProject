import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class JsonReader
{
	public static HashMap<String,String> read(String filename)
	throws Exception
	{
		BufferedReader br = new BufferedReader(
								new FileReader(
									new File(filename)));

		String line = br.readLine().replaceAll("\"", "");
		String pairs[] = line.substring(1,line.length()-1).split(",");

		HashMap<String,String> values = new HashMap<>();
		String tmp[];

		for (String pair : pairs)
		{
			tmp = pair.split(":");
			values.put(tmp[0], tmp[1]);
		}

		return values;
	}
}