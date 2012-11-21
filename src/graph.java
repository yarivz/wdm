import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;


public class graph {
	
	Vector<edge> edgeVec;
	Vector<vertex> vertexVec;
	int stopCounter;
	
	public graph()
	{
		edgeVec = new Vector<edge>();
		vertexVec = new Vector<vertex>();
		stopCounter = 0;
	}
	
	public void clean()
	{
		int k = edgeVec.size();
		//clean edges
		Iterator<edge> edItr = edgeVec.iterator();
		for(int i=0;i<k;i++)
		{
			
			edge e = edItr.next();
			if(!e.vt.alive)
			{
				vertex v = vertexVec.get(vertexVec.indexOf(e.vs));
				v.outgoingEdges.remove(e);
				edItr.remove();
			}
		}
		
		//clean vertices
		Iterator<vertex> verItr = vertexVec.iterator();
		k = vertexVec.size();
		for(int i=0;i<k;i++)
		{
			vertex v = verItr.next();
			if(!v.alive)
			{
				verItr.remove();
			}
		}
	}
	
	public void calcPr(double epsilon,double damping)
	{
		while(stopCounter<vertexVec.size())
		{
			Iterator<vertex> verItr = vertexVec.iterator();
			while(verItr.hasNext())
			{
				vertex v = verItr.next();
				if(!v.stop)
				{
					Iterator<edge> neighIter = v.incomingEdges.iterator();
					double tempPR = 0;
					while(neighIter.hasNext())
					{
						edge e = neighIter.next();
						tempPR += (1-damping)*e.vs.newPR/e.vs.outgoingEdges.size();
					}
					tempPR += damping/vertexVec.size();
					v.newPR = tempPR;
					// if the difference from previous iteration is less than epsilon we'll
					// stop trying to update vertex's pageRank
					if(Math.abs(v.oldPR-v.newPR)<epsilon)
					{
						v.stop = true;
						stopCounter++;
					}
				}
			}
			verItr = vertexVec.iterator();
			//updating oldPR at the end of an iteration
			while(verItr.hasNext())
			{
				vertex v = verItr.next();
				v.oldPR = v.newPR;
			}
		}
	}

	public void generateGraph(String[] args, double initPR) throws FileNotFoundException
	{
		boolean flag1,flag2;
		
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
			  vertex title = new vertex(args[j],initPR);
			  title.alive = true;
		
			  if(!vertexVec.contains(title))
				  vertexVec.add(title);
			  else						// If vertex was already found on another text file we'll just update his statues for alive whice means that there's a file like by that name
			  {
				  flag1 = true;
				  vertexVec.get(vertexVec.indexOf(title)).alive = true;
			  }
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null) 
			  {
				  for(int i=0;i+8<strLine.length();i++)
				  {
					  if(strLine.substring(i, i+8).equals("<a href="))
					  {
						  flag2=false;
						  vertex v = new vertex(strLine.substring(i+9, strLine.indexOf('"', i+10)),initPR);
						  if(vertexVec.contains(v))
							  flag2=true;
						  
						  // for the case that the file we're handling now is already a vertex
						  if(flag1)
						  {
							  vertex temp1 = vertexVec.get(vertexVec.indexOf(title));
							  //for the case that the new vertex we found was found before
							  if(flag2)
							  {
								  vertex temp2 = vertexVec.get(vertexVec.indexOf(v));
								  edge e = new edge(temp1,temp2);
								  if(!edgeVec.contains(e))
								  {
									  edgeVec.add(e);
									  temp2.incomingEdges.add(e);
									  temp1.outgoingEdges.add(e);
								  }
							  }
							  //for the case that the new vertex we found wasn't found before
							  else
							   {
								  edge e = new edge(temp1,v);
								  vertexVec.add(v);
								  edgeVec.add(e);
								  v.incomingEdges.add(e);
								  temp1.outgoingEdges.add(e);
							  }
						  }
						  // for the case that the file we're handling now isn't a vertex yet
						  else
						  {
							  //for the case that the new vertex we found was found before
							  if(flag2)
							  {
								  vertex temp2 = vertexVec.get(vertexVec.indexOf(v));
								  edge e = new edge(title,temp2);
								  edgeVec.add(e);
								  temp2.incomingEdges.add(e);
								  title.outgoingEdges.add(e);
							  }
							  //for the case that the new vertex we found wasn't found before
							  else
							  {
								  vertexVec.add(v);
								  edge e = new edge(title,v);
								  edgeVec.add(e);
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
	}

	public void print()
	{
		Iterator<vertex> verItr = vertexVec.iterator();
		
		while(verItr.hasNext())
		{
			vertex v = verItr.next();
			System.out.println("VERTEX NAME: "+v.name);
			System.out.println("------------\n");
			Iterator<edge> edItr = v.incomingEdges.iterator();
			System.out.println("INCOMING EDGES:");
			System.out.println("---------------");
			while(edItr.hasNext())
			{
				edge e = edItr.next();
				System.out.println(e.vs.name+"->"+e.vt.name);
			}
			System.out.println();
			edItr = v.outgoingEdges.iterator();
			System.out.println("OUTGOING EDGES:");
			System.out.println("---------------");
			while(edItr.hasNext())
			{
				edge e = edItr.next();
				System.out.println(e.vs.name+"->"+e.vt.name);
			}
			System.out.println("\n--------------------------------------------------");
		}
	}

	public void genCleanCalc(String[] args, double initPR,double epsilon,double damping) throws FileNotFoundException{
		generateGraph(args,initPR);
		clean();
		calcPr(epsilon,damping);
	}

	public void genCleanPrint(String[] args, double initPR) throws FileNotFoundException{
		generateGraph(args,initPR);
		clean();
		print();
	}
}