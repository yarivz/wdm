import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Iterator;
 



public class graphGenerator{	
	
	public static void main(String[] args) throws FileNotFoundException
	{ 
		boolean flag1,flag2;
		graph g = new graph();
		for(int j=0;j<args.length;j++)
		{
			flag1=false;
			try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(args[j]);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  vertex title = new vertex(args[j]);
			  title.alive = true;
			  if(!g.vertexVec.contains(title))
				  g.vertexVec.add(title);
			  else
			  {
				  flag1 = true;
				  g.vertexVec.get(g.vertexVec.indexOf(title)).alive = true;
			  }
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null) 
			  {
				  for(int i=0;i+8<strLine.length();i++)
				  {
					  if(strLine.substring(i, i+8).equals("<a href="))
					  {
						  flag2=false;
						  vertex v = new vertex(strLine.substring(i+9, strLine.indexOf('"', i+10)));
						  if(g.vertexVec.contains(v))
							  flag2=true;
						  
						  if(flag1)
						  {
							  vertex temp1 = g.vertexVec.get(g.vertexVec.indexOf(title));
							  if(flag2)
							  {
								  vertex temp2 = g.vertexVec.get(g.vertexVec.indexOf(v));
								  edge e = new edge(temp1,temp2);
								  if(!g.edgeVec.contains(e))
								  {
									  g.edgeVec.add(e);
									  temp2.incomingEdges.add(e);
									  temp1.outgoingEdges.add(e);
								  }
							  }
							  else
							  {
								  edge e = new edge(temp1,v);
								  g.vertexVec.add(v);
								  g.edgeVec.add(e);
								  v.incomingEdges.add(e);
								  temp1.outgoingEdges.add(e);
							  }
						  }
						  else
						  {
							  if(flag2)
							  {
								  vertex temp2 = g.vertexVec.get(g.vertexVec.indexOf(v));
								  edge e = new edge(title,temp2);
								  g.edgeVec.add(e);
								  temp2.incomingEdges.add(e);
								  title.outgoingEdges.add(e);
							  }
							  else
							  {
								  g.vertexVec.add(v);
								  edge e = new edge(title,v);
								  g.edgeVec.add(e);
								  v.incomingEdges.add(e);
								  title.outgoingEdges.add(e);
							  }
						  }
						  	
						  i = strLine.indexOf('"', i+9);
					  }
				  }
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		}
		g.clean();
		double epsilon = 0.01;
		double damping = 0.85;
		g.calcPr(epsilon,damping);
		
		//print
		Iterator<vertex> verItr = g.vertexVec.iterator();
		double sum=0;
		while(verItr.hasNext())
		{
			vertex v = verItr.next();
			System.out.println(v.name +": "+ v.newPR);
			sum += v.newPR;
		}
		System.out.println("sum: "+sum);
	}//end of main
}//end of class