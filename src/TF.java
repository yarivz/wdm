import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zury
 * Date: 11/16/12
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TF {

	private HashMap<String,Double> tfValues;
	HashMap<String,HashMap<String, Double>> docstf;
	int wordCount = 0;

	public TF(){
		docstf = new HashMap<String,HashMap<String, Double>>();
	}

	public void calculateTF(String[] docs) {
		 for(int j=0; j<docs.length;j++){
			try{
					// Open the file that is given
					// command line parameter
					FileInputStream fstream = new FileInputStream(docs[j]);
					// Get the object of DataInputStream
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					tfValues = new HashMap<String, Double>();
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
									tfValues.put(s,1.0);
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
			}
		for(Map.Entry<String,Double> entry : tfValues.entrySet()){
			entry.setValue(entry.getValue()/wordCount);
		}
	}

	public double getTF(String doc,String word){
		word = word.toLowerCase();
		HashMap<String, Double> tf = docstf.get(doc);
		if(tfValues.containsKey(word)){
			return tfValues.get(word);
		}else{
			return 0;
		}
	}
}
