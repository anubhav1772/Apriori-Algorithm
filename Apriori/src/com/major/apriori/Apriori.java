package com.major.apriori;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.major.tuple.Tuple;

public class Apriori 
{
    Set<Tuple> C;
    Set<Tuple> L;
    int min_support;
    int dataset[][];
    
    public Apriori()
    {
       C=new HashSet<>();
       L=new HashSet<>();
       min_support=2;     
    }
    
    /* In this step, we eliminate itemsets having support count less than required minimum support count. */
    /* Conversion from C(k) to L(k) */
    /* i.e. Candidate itemset of size k  to frequent itemset of size k. */
    public void prune()   
    {
    	L.clear();
    	Iterator<Tuple> it=C.iterator();
    	Tuple record;
    	while(it.hasNext())
    	{
    		record=it.next();
    		if(record.support>=min_support)
    		{
    		   L.add(record);
    		}
    	}
    	
    	System.out.println("xxxxx----L----xxxxx");
    	for(Tuple t:L)
    	{
    		System.out.println(t.itemset+" : "+t.support);
    	}
    	
    }
    
    public int count(Set<Integer> s)
    {
    	int support=0;
    	for(int i=0;i<dataset.length;i++)
    	{
    		boolean flag=false;
    		int count=0;
    		Iterator<Integer> it=s.iterator();
    		int item_id;
    		while(it.hasNext())
    		{
    			item_id=(int)it.next();
    			for(int j=0;j<dataset[i].length;j++)
    			{
    				if(dataset[i][j]==item_id)
    				{
    					flag=true;
    					++count;
    					break;
    				}
    			}
    			if(!flag)
    			{
    				break;
    			}
    			else
    			{
    				flag=false;
    			}
    		}
    		if(count==s.size())
    		{
    			++support;
    		}
    		
    	}
    	return support;
    }
    
    public void generateFrequentItemsets()
    {
    	
    }
    
    public void readDataset_csv()throws IOException
    {
    	String csv="/home/anubhav55182/eclipse-workspace/Apriori/apriori.csv";
    	BufferedReader br=null;
    	String line="";
    	Map<Integer,List<Integer>> map;       // <Transaction_id,List of Itemsets>
    	List<Integer> temp;
    	try
    	{
    		br=new BufferedReader(new FileReader(csv));
    		map=new HashMap<Integer,List<Integer>>();
    		//line=br.readLine();
    		String transaction[];             // consists of purchased items id in a particular transaction
    		int i=1;
    		while((line=br.readLine())!=null)
    		{
    			transaction=line.split(",");
    			Integer id;                    // items id
    			temp=new LinkedList<Integer>();
    			for(String s:transaction)
    			{
    				id=Integer.parseInt(s);
    				temp.add(id);
    			}
    			map.put(i,temp);
    			++i;
    			
    		}
    		
    		Set<Integer> keyset=map.keySet();  // get the keys in map
    		dataset=new int[keyset.size()][];
    		Iterator<Integer> it=keyset.iterator();
    		int count=0;
    		while(it.hasNext())
    		{
    			temp=map.get(it.next());       // getting itemsets of each transaction
    			Integer items[]=temp.toArray(new Integer[0]);
    			dataset[count]=new int[items.length];
    			for(int j=0;j<items.length;j++)
    			{
    				dataset[count][j]=items[j].intValue();
    			}
    			++count;
    		}
    		
    		
    		
    	}
    	catch(FileNotFoundException fnfe)
    	{
    		fnfe.printStackTrace();
    	}
    	catch(IOException ioe)
    	{
    		ioe.printStackTrace();
    	}
    	finally
    	{
    		if(br!=null)
    		{
    			try
    			{
    				br.close();
    			}
    			catch(IOException e)
    			{
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    
    
	public static void main(String[] args) 
	{
       Apriori apriori=new Apriori();
       

	}

}
