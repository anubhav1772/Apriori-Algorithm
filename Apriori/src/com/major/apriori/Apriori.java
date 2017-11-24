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
    public static int step=1;
    
    public Apriori()
    {
       C=new HashSet<Tuple>();
       L=new HashSet<Tuple>();
       min_support=2;     
    }
    
    /* In this step, we eliminate itemsets having support count less than required minimum support count. */
    /* Conversion from C(k) to L(k) */
    /* i.e. Candidate itemset of size k  to frequent itemset of size k. */
    public void prune()   
    {
    	L.clear();
    	Iterator<Tuple> it=C.iterator();
    	/* loop to print candidate itemsets along with their support count. */
    	System.out.printf("xxxxx----(C%d)----xxxxx\n",step);
    	for(Tuple t:C)
    	{
    		System.out.println(t.itemset+" : "+t.support);
    	}
    	
    	Tuple record;
    	while(it.hasNext())
    	{
    		record=it.next();
    		if(record.support>=min_support)
    		{
    		   L.add(record);
    		}
    	}
    	
    	/* loop to print itemsets and their support count after pruning. */
    	System.out.printf("xxxxx----(L%d)----xxxxx\n",step);
    	if(!L.isEmpty())
    	{
    		for(Tuple t:L)
        	{
        		System.out.println(t.itemset+" : "+t.support);
        	}
    	}
    	else
    	{ 
    		System.out.println("Empty Set.");
    		
    	}
    	++step;
    	
    }
   
    /* Function to count the number of occurences of a given itemset in the entire transaction dataset. */
    /* step for support count of an itemset. */
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
    	boolean flag=true;
    	int size=1;
    	Set<Set> candidate_set=new HashSet<Set>();
    	int element=0;
    	while(flag)    // while loop 1
    	{
    		C.clear();
    		candidate_set.clear();
    		Iterator<Tuple> it=L.iterator();
    		Set<Integer> set;
    		while(it.hasNext())  // while loop 2
    		{
    			set=it.next().itemset;
    			Iterator<Tuple> _it=L.iterator();
    			Tuple tup;
    			while(_it.hasNext()) // while loop 3
    			{
    				tup=_it.next();
    				Iterator<Integer> _it_=tup.itemset.iterator();
    				while(_it_.hasNext())
    				{
    					element=(int) _it_.next();
    					set.add(element);
    					if(set.size()!=size)
    					{
    						Integer arr[]=set.toArray(new Integer[0]);
    						Set<Integer> temp=new HashSet<Integer>();
    						for(Integer i:arr)
    						{
    							temp.add(i);
    						}
    						candidate_set.add(temp);
    						set.remove(element);
    					}
    				}
    			}  // end of while loop 3
    			
    		} // end of while loop 2
    		
    		Iterator<Set> iterator=candidate_set.iterator();
        	Set s;
        	while(iterator.hasNext())
        	{
        		s=iterator.next();
        		C.add(new Tuple(s,count(s)));
        	}
        	prune();
        	if(L.size()<=1)
        	{
        		flag=false;
        	}
        	++size;
    		
    			
    	} // end of while loop 1
    	
    	System.out.println("\n**** Most Ftequent Itemset ****");
    	if(L.isEmpty())
    	{
    		System.out.printf("L%d doesn't contains any itemset with support count greater than 2.\n",(step-1));
    		System.out.printf("Hence, L%d itemset(s) is the final result.",(step-2));
    	}
    	else
    	{
    		for(Tuple tuple : L) 
        	{
    			System.out.println(tuple.itemset + " : " + tuple.support);
    		}
    	}
    	
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
    		C.add(new Tuple(s,count(s)));
    	}
    	/* to print first initial candidate elements along with their support */
    /*	Iterator<Tuple> test=C.iterator();
    	while(test.hasNext())
    	{
    		Tuple t=test.next();
    		Set<Integer> set=t.itemset;
    		Iterator<Integer> itt=set.iterator();
    		System.out.print("[ ");
    		while(itt.hasNext())
    		{
    			
    			System.out.print(itt.next()+" ");
    			
    		}
    		System.out.println("] =>"+t.support+"");
    		
    	}*/
    	prune();
    	generateFrequentItemsets();
    }
    
	public static void main(String[] args) 
	{
       Apriori apriori=new Apriori();
       apriori.init();

	}

}
