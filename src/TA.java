import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: zury
 * Date: 11/18/12
 * Time: 12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class TA {
	
	TF tf;
	graph g;
	Vector<String> words;
	String[] files;
	int flag;
	
	public TA(TF tf, graph g, Vector<String> words, String[] files, int flag)
	{
		this.tf = tf;
		this.g = g;
		this.words = words;
		this.files = files;
		this.flag = flag;
	}
	
	public void run()
	{
		Vector<Vector<String>> table = new Vector<Vector<String>>();
		// build the table
		for(int i=0;i<words.size();i++)
		{
			//add your thing here
			table.add();
		}
	}
}
