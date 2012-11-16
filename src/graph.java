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
}
