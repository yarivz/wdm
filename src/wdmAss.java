import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;

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
		double initPR = 1;					// Initial PageRank value
		double epsilon = 0.01;				// The value for which we will stop trying to update a vertex's pageRank
		double damping = 0.3;				// Damping Factor
        double startTime = System.currentTimeMillis();

		String option = args[0];
        String[] files = new String[args.length-1];
		Arrays.copyOfRange(args,1,files.length);
		if(option.equals("-graph")){
			graph g = new graph();
			g.generateGraph(args,initPR);  	// Creates the graph
			g.clean(); 							// Cleans the graph from dead links
			g.print();							// Prints the graph
			return;
		}
		else if(option.equals("-pr")){
			graph g = new graph();
			g.generateGraph(args,initPR);  	// Creates the graph
			g.clean(); 							// Cleans the graph from dead links
			g.calcPr(epsilon,damping);			// Calculates PageRank for each vertex
			double estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("Execution time: "+estimatedTime);
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
			doc[0] = args[2];
			tf.calculateTF(doc);
			System.out.println(tf.getTF(doc[0],args[3]));
			return;
		}
		else if(option.equals("-ta")){

		}
		else if(option.equals("-help")){
			System.out.println("Options:\n-graph\tCreate a graph from the given files\n");
			System.out.println("-help\tDisplay command line options\n");
			System.out.println("-pr\tCalculate PageRank values for a graph generated from files\n");
			System.out.println("-tf\tCalculate TF value for a word in a given file\n");
			System.out.println("-ta\tRun Threshold Algorithm for a given set of words & documents and display most relevant documents\n");


		}
		else{
			System.out.println("Usage: wmdAss.jar [options] file1 file2...");
		}


	}
}
