
public class stringScore implements Comparable<stringScore>{
	
	String name;
	double score;

	public stringScore(String name, double tf)
	{
		this.name = name;
		this.score = tf;
	}


	public int compareTo(stringScore other) 
	{
		if(score-other.score>0)
			return 1;
		else
			if(score-other.score<0)
				return -1;
			else
				return 0;
	}
	
	public boolean equals(Object other)
	{
		return name.equals(other.toString());
	}
	
	public String toString()
	{
		return name;
	}
	
}
