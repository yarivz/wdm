
public class edge {
	vertex vs;
	vertex vt;
	
	public edge(vertex v1,vertex v2)
	{
		vs = v1;
		vt = v2;
	}
	
	public boolean equals(edge e)
	{
		return (vs.equals(e.vs)&& vt.equals(e.vt));
	}

}