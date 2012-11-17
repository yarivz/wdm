import java.util.Vector;


public class vertex {

	String name;
	Vector<edge> incomingEdges;
	Vector<edge> outgoingEdges;
	boolean alive;
	double oldPR;
	double newPR;
	boolean stop;
	
	public vertex(String name,double PR)
	{
		this.name = name;
		incomingEdges = new Vector<edge>();
		outgoingEdges = new Vector<edge>();
		alive = false;
		oldPR = PR;
		newPR = PR;
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