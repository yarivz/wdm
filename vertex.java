import java.util.Vector;


public class vertex {

	String name;
	Vector<edge> incomingEdges;
	Vector<edge> outgoingEdges;
	boolean alive;
	double oldPR;
	double newPR;
	boolean stop;
	
	public vertex(String name)
	{
		this.name = name;
		incomingEdges = new Vector<edge>();
		outgoingEdges = new Vector<edge>();
		alive = false;
		oldPR = 1;
		newPR = 1;
		stop = false;
	}
	
	public boolean equals(Object v)
	{
		return name.equals(v.toString());
	}
	
	public String toString()
	{
		return name;
	}
}