
public class webTf implements Comparable<webTf>{
	
	String name;
	double tf;

	public webTf(String name, double tf)
	{
		this.name = name;
		this.tf = tf;
	}


	public int compareTo(webTf other) 
	{
		if(tf-other.tf>0)
			return 1;
		else
			if(tf-other.tf<0)
				return -1;
			else
				return 0;
	}
}
