import java.io.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: zury
 * Date: 11/16/12
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TF {

	private HashMap<String,Integer> tfValues;
	int wordCount = 0;

	public int calculateTF(String doc, String word) {
		tfValues = new HashMap<String, Integer>();
			try{
				// Open the file that is given
				// command line parameter
				FileInputStream fstream = new FileInputStream(doc);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				//Read File Line By Line
				while ((strLine = br.readLine()) != null)
				{
					strLine.toLowerCase();
					String[] words = strLine.split("\\W");
					for (String s:words){
						if(!s.isEmpty()){
							if (tfValues.containsKey(s))
							{
								tfValues.put(s,tfValues.get(s)+1);
							}else{
								tfValues.put(s,1);
							}
							wordCount++;
						}
					}

				}
				//Close the input stream
				in.close();
			}catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		word = word.toLowerCase();
		if(tfValues.containsKey(word)){
			return ((tfValues.get(word))/wordCount);
		}else{
			return 0;
		}
	}
}
