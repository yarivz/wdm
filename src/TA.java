import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
		Collections.sort(words);
		this.files = files;
		this.flag = flag;
	}
	
	public void run()
	{
		Comparator<stringScore> comparator = Collections.reverseOrder();
		Vector<wordWebTf> wwfVec = new Vector<wordWebTf>();   //Vector that contains objects representing words with a list of their TF values in each of the documents
		// build the table
		for(int i=0;i<words.size();i++)
		{
			String word = words.get(i).toLowerCase();   //change to lowercase to guarantee case insensitivity
			wordWebTf wwf = new wordWebTf(word);
			String website;
			Double tfScoreOfWord;
			for(Map.Entry<String,HashMap<String,Double>> entry : tf.getDocstf().entrySet())
			{
				HashMap<String,Double> tfMap = entry.getValue();
				website = entry.getKey();
				tfScoreOfWord = tfMap.get(word);
				if (tfScoreOfWord == null) tfScoreOfWord = 0.0;
				stringScore wt = new stringScore(website,tfScoreOfWord);
				wwf.wtVec.add(wt);
			}
			
			//sort
			Collections.sort(wwf.wtVec,comparator);
			wwfVec.add(wwf);
		}
		
		
		//TA algo
	  	Vector<stringScore> resultVec = new Vector<stringScore>();
		double threshold = 0;
		int index = 0;
		String website;
		double result;
		double tf;

		boolean over = false;
		while(!over)
		{
			for(int j=0;j<wwfVec.size();j++)
			{
				result=0;
				tf = 0;

				website = wwfVec.get(j).wtVec.get(index).name;
				tf = wwfVec.get(j).wtVec.get(index).score;
				vertex v = new vertex(website,1);
				threshold += tf * g.vertexVec.get(g.vertexVec.indexOf(v)).newPR;
				stringScore ssTemp = new stringScore(website,tf);
				if(resultVec.contains(ssTemp))
				{
					continue;         //skip over websites already added to the result list
				}
				result = tf;
				stringScore ss = new stringScore(website,0);
				for(int k=0;k<wwfVec.size();k++)
				{
					if(k!=j)
					{
						if(flag==1)   //the AND operator was given
							result += wwfVec.get(k).wtVec.get(wwfVec.get(k).wtVec.indexOf(ss)).score;
						else         //the OR operator was given
							result = Math.max(result, wwfVec.get(k).wtVec.get(wwfVec.get(k).wtVec.indexOf(ss)).score);
					}
				}
				if(flag==1){
					result = result/wwfVec.size();
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
