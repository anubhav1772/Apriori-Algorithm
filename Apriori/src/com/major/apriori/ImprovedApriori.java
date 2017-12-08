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

import com.major.tuple.Record;
import com.major.tuple.Tuple;

public class ImprovedApriori 
{
	 Set<Record> C;
	 Set<Record> L;
	 int min_support;
	 int dataset[][];
	 public static int step=1;
	    
	 public ImprovedApriori()
	 {
	    C=new HashSet<Record>();
	    L=new HashSet<Record>();
	    min_support=2;     
	 }
	public void readDataset_csv()throws IOException
    {
    	String csv="/home/anubhav55182/eclipse-workspace/Apriori/dataset.csv";
    	BufferedReader br=null;
    	String line="";
    	Map<Integer,List<Integer>> map;       // <Transaction_id,List of itemsets>
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
    				//System.out.print(dataset[count][j]+" ");
    			}
    			//System.out.println();
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
	public void prune()   
    {
    	L.clear();
    	Iterator<Record> it=C.iterator();
    	for(Record t:C)
    	{
    		System.out.println(t.itemset+" : "+t.support+" : "+t.transactions);
    	}
    	System.out.println("*************************************************");
    	
    	Record record;
    	while(it.hasNext())
    	{
    		record=it.next();
    		if(record.support>=min_support)
    		{
    		   L.add(record);
    		}
    	}
    	if(!L.isEmpty())
    	{
    		for(Record t:L)
        	{
        		System.out.println(t.itemset+" : "+t.support+" : "+t.transactions);
        	}
    	}
    	else
    	{ 
    		System.out.println("Empty Set.");
    		
    	}
    	++step;
    	
    }
	
	public void init()
    {
    	try
    	{
    		readDataset_csv();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	
    	Set<Integer> candidate_set=new HashSet<Integer>();
    	for(int i=0;i<dataset.length;i++) 
    	{
    		for(int j=0;j<dataset[i].length;j++)
    		{
    			candidate_set.add(dataset[i][j]);
    		}
    	}
    	Iterator<Integer> it=candidate_set.iterator();
    	int item;
    	while(it.hasNext())
    	{
    		item=(int) it.next();
    		Set<Integer> s = new HashSet<>();
			s.add(item);
    		C.add(new Record(s,count(s),-1,getTransactionsId(s)));
    	}
        
    	prune();
    	//generateFrequentItemsets();
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
	
	public Set<Integer> getTransactionsId(Set<Integer> s)
	{
		Set<Integer> transactions=new HashSet<Integer>();
		for(int i=0;i<dataset.length;i++)
		{
			boolean flag=false;
			int count=0;
			Iterator<Integer> it=s.iterator();
			int item_id;
			while(it.hasNext())
			{
               item_id=(int) it.next();
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
    			transactions.add(i);
    		}
		}
		return transactions;
	}
	
	public void printCandidate()
	{
		Iterator<Record> it=C.iterator();
		while(it.hasNext())
		{
			Record rec=(Record) it.next();
			System.out.println(rec.itemset+" "+rec.support+" "+rec.min+" "+rec.transactions);
		}
	}

	public static void main(String[] args) 
	{
		ImprovedApriori apriori=new ImprovedApriori();
	   // long start=System.currentTimeMillis();
	    apriori.init();
	   // long end=System.currentTimeMillis();
	    //System.out.println("\nTime taken to run the Improved Apriori Algorithm= "+(end-start)+"ms");

	}

}
