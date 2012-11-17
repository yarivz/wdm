
import java.io.FileNotFoundException;
import java.util.Iterator;
 



public class mainClass{	
	
	public static void main(String[] args) throws FileNotFoundException
	{ 
		double initPR = 1;					// Initial PageRank value
		double epsilon = 0.01;				// The value for which we will stop trying to update a vertex's pageRank
		double damping = 0.3;				// Damping Factor
		
		double startTime = System.currentTimeMillis();
		graph g = new graph();
		g.generateGraph(args,initPR);  	// Creates the graph
		g.clean(); 							// Cleans the graph from dead links
		g.print();							// Prints the graph
		
		
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
	}//end of main
}//end of class