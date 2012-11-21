import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
		Comparator<stringScore> comparator = Collections.reverseOrder();
		Vector<wordWebTf> wwfVec = new Vector<wordWebTf>();
		// build the table
		for(int i=0;i<words.size();i++)
		{
			wordWebTf wwf = new wordWebTf(words.get(i));
			for(Map.Entry<String,HashMap<String,Double>> entry : tf.getDocstf().entrySet())
			{
				HashMap<String,Double> tfMap = entry.getValue();
				String website = entry.getKey();
				Double tfScoreOfWord = tfMap.get(words.elementAt(i));
				if (tfScoreOfWord == null) tfScoreOfWord = 0.0;
				stringScore wt = new stringScore(website,tfScoreOfWord);
				wwf.wtVec.add(wt);
			}
			
			//sort
			Collections.sort(wwf.wtVec,comparator);
			//add your thing here
			wwfVec.add(wwf);
		}
		
		
		//TA algo
	
		Vector<stringScore> resultVec = new Vector<stringScore>();
		double threshold;
		int index = 0;
		
		boolean over = false;
		while(!over)
		{
			threshold=0;
			for(int j=0;j<wwfVec.size();j++)
			{
				double result=0;
				String website;
				double tf = 0;
			
				website = wwfVec.get(j).wtVec.get(index).name;
				tf = wwfVec.get(j).wtVec.get(index).score;
				vertex v = new vertex(website,1);
				threshold += tf * g.vertexVec.get(g.vertexVec.indexOf(v)).newPR;;
				stringScore ssTemp = new stringScore(website,tf);
				if(resultVec.contains(ssTemp))
					continue;
				
				for(int k=0;k<wwfVec.size();k++)
				{
					result = tf;
					if(k!=j)
					{
						stringScore ss = new stringScore(website,0);
						if(flag==1)
							result += wwfVec.get(k).wtVec.get(wwfVec.get(k).wtVec.indexOf(ss)).score;
						else
							result = Math.max(result, wwfVec.get(k).wtVec.get(wwfVec.get(k).wtVec.indexOf(ss)).score);
					}
				}
				ssTemp.score = result * g.vertexVec.get(g.vertexVec.indexOf(v)).newPR;
				
				if(resultVec.size()<3)
				{
					resultVec.add(ssTemp);
					Collections.sort(resultVec);
				}
				else
				{
					if(ssTemp.score>resultVec.elementAt(0).score)
					{
						resultVec.setElementAt(ssTemp,0);
						Collections.sort(resultVec);
					}
				}
			}
			index++;
			threshold = threshold/wwfVec.size();
			if(resultVec.size()>=3 && resultVec.elementAt(0).score>=threshold)
				over=true;
		}
		
		//print
        System.out.println("TA scores:");
		for(int i=resultVec.size()-1,k=0;k<3;i--,k++)
		{
			System.out.println(""+(k+1)+") "+resultVec.elementAt(i).name+" "+wdmAss.df.format(resultVec.elementAt(i).score));
		}
	}
}
