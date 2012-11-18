import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: zury
 * Date: 11/17/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class wdmAss {

	public static void main(String[] args) throws FileNotFoundException
	{
		int FLAG_OR = 0, FLAG_AND = 0;
		double initPR = 1;					// Initial PageRank value
		double epsilon = 0.01;				// The value for which we will stop trying to update a vertex's pageRank
		double damping = 0.3;				// Damping Factor
        

		String option = args[0];

		if(option.equals("-graph")){
			String[] files = Arrays.copyOfRange(args,1,args.length-1);
			graph g = new graph();
			g.generateGraph(files,initPR);  	// Creates the graph
			g.clean(); 							// Cleans the graph from dead links
			g.print();							// Prints the graph
			return;
		}
		else if(option.equals("-pr")){
			String[] files = Arrays.copyOfRange(args,1,args.length-1);
			graph g = new graph();
			double startTime = System.currentTimeMillis();
			g.generateGraph(files,initPR);  	// Creates the graph
			g.clean(); 							// Cleans the graph from dead links
			g.calcPr(epsilon,damping);			// Calculates PageRank for each vertex
			double estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("Execution time: " + estimatedTime);
			System.out.println();

			//print
			System.out.println("PageRanks:");
			Iterator<vertex> verItr = g.vertexVec.iterator();
			while(verItr.hasNext())
			{
				vertex v = verItr.next();
				System.out.println(v.name +": "+ v.newPR);
			}
		}
		else if(option.equals("-tf")){
			TF tf = new TF();
			String[] doc = new String[1];
			doc[0] = args[1];
			tf.calculateTF(doc);
			System.out.println("TF: "+tf.getTF(doc[0],args[2]));
			return;
		}
		else if(option.equals("-ta")){
			Vector<String> words = new Vector<String>();
			int k=0;
			if(args[1].contains("(")){             //parse the word set and determine the boolean operator
				words.add(args[1].substring(1).toLowerCase());
				for(k=2;!args[k].contains(")");k++){
					if(args[k].equals("OR")){
						FLAG_OR = 1;
					}
					else if(args[k].equals("AND")){
						FLAG_AND = 1;
					}
					else{
						words.add(args[k].toLowerCase());
					}
				}
				words.add(args[k].substring(0,args[k].indexOf(")")));   //add last word of the set
			}
			String[] files = Arrays.copyOfRange(args,k+1,args.length-1);
			graph g = new graph();
			g.generateGraph(files,initPR);  	// Creates the graph
			g.clean(); 							// Cleans the graph from dead links
			g.calcPr(epsilon,damping);			// Calculates PageRank for each vertex
			TF tf = new TF();
			tf.calculateTF(files);
			
			int flag = Math.max(FLAG_OR, FLAG_AND);
			TA ta = new TA(tf,g,words,files,flag);
			ta.run();











		}
		else if(option.equals("-help")){
			System.out.println("Options:\n-graph\tCreate a graph from the given files\n");
			System.out.println("-help\tDisplay command line options\n");
			System.out.println("-pr\tCalculate PageRank values for a graph generated from files\n");
			System.out.println("-tf DOCUMENT WORD\tCalculate TF value for a word in a given file\n");
			System.out.println("-ta (WORDS)\tRun Threshold Algorithm for a given set of words & documents and display most relevant documents\n");
        }
		else{
			System.out.println("Usage: wmdAss.jar [options] file1 file2...");
		}


	}
}
