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

public class ImprovedApriori 
{
	 Set<Record> C;
	 Set<Record> L;
	 Set<Record> L1; // L1= first L set
	 int min_support;
	 int dataset[][];
	 HashMap<Integer,Integer> item_support; //stores item id and its corresponding support count
	 public static int step=1;
	 
	 public ImprovedApriori()
	 {
	    C=new HashSet<Record>();
	    L=new HashSet<Record>();
	    L1=new HashSet<Record>();
	    item_support=new HashMap<Integer,Integer>();
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
    	/*for(Record t:C)
    	{
    		System.out.println(t.itemset+" : "+t.support+" : "+t.transactions);
    	}
    	System.out.println("*************************************************");*/
    	
    	Record record;
    	while(it.hasNext())
    	{
    		record=it.next();
    		if(record.support>=min_support)
    		{
    		   L.add(record);
    		}
    		
    	}
    	if(L1.isEmpty())
    	{
    		L1.addAll(L);
    		for(Record r:L1)
    		{
    			item_support.put(r.itemset.iterator().next(), r.support);
    		}
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
    	generateFrequentItemSet();
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
	
	public void generateFrequentItemSet()
	{
		boolean flag=true;
    	int size=1;
    	Set<Set> candidate_set=new HashSet<Set>();
    	int element=0;
    	while(flag) //loop 1
    	{
    		C.clear();
    		candidate_set.clear();
    		Iterator<Record> it=L.iterator();
    		Set<Integer> set;
    		while(it.hasNext())  // while loop 2
    		{
    			set=it.next().itemset;
    			Iterator<Record> _it=L1.iterator();
    			Record rec;
    			while(_it.hasNext()) // while loop 3
    			{
    				rec=_it.next();
    				Iterator<Integer> _it_=rec.itemset.iterator();
    				while(_it_.hasNext())
    				{
    					element=(int) _it_.next();
    					set.add(element);    // add element to set
    					if(set.size()==size)
    					{
    						candidate_set.add(set);
    					}
    					
    				}
    			    set.remove(element);     // remove element from set
    			}  // end of while loop 3
    			
    		} // end of while loop 2
    		
    		Iterator<Set> iterator=candidate_set.iterator();
        	Set s;
        	System.out.println("=================================================================");
        	while(iterator.hasNext())
        	{
        		s=(Set) iterator.next();
        		Iterator<Integer> itt=s.iterator();
        		Integer i, min=Integer.MAX_VALUE;
                
        		while(itt.hasNext())
        		{
        			i=itt.next();
        			min=Math.min(min,item_support.get(min));
        			
        		}
        	   Set<Integer> t=getTransactions(min);
        	   C.add(new Record(s,sup_count(s,t),min,t));
        	   System.out.println(s+" : "+t+" : "+min+" : "+t);
        		
        	}
        	System.out.println("=================================================================");
        	prune();
        	if(L.size()<=1)
        	{
        		flag=false;
        	}
        	++size;
    	}
    	
   /* 	System.out.println("\n**** Most Ftequent Itemset ****");
    	if(L.isEmpty())
    	{
    		System.out.printf("L%d doesn't contains any itemset with support count greater than 2.\n",(step-1));
    		System.out.printf("Hence, L%d itemset(s) is the final result.",(step-2));
    	}
    	else
    	{
    		for(Record record : L) 
        	{
    			System.out.println(record.itemset + " : " +record.support+" : "+record.min+" : " +record.transactions);
    		}
    	} */
    	
	}
	
	public int sup_count(Set<Integer> items,Set<Integer> transactions)
	{
		int sup=0;
		Iterator<Integer> it=transactions.iterator();
		Integer i;
		while(it.hasNext())
		{
		  i=it.next();
		  int count=0;
		  Iterator<Integer> itt=items.iterator();
		  int item;
		  while(itt.hasNext())
		  {
			  item=(int) itt.next();
			  for(int j=0;j<dataset[i].length;j++)
			  {
				  if(dataset[i][j]==item)
				  {
					  ++count;
					  break;
				  }  
			  }
		  }
		  if(count==items.size())
		  {
			  ++sup;
		  }
		}
		return sup;
	}
	
	public Set<Integer> getTransactions(int item_id)
	{
		Set<Integer> trans=new HashSet<Integer>();
		Iterator<Record> t=L1.iterator();
		while(t.hasNext())
		{
			Record rr=t.next();
			Set<Integer> itemset=rr.itemset;
		    Iterator<Integer> itt=itemset.iterator();
			int i;
			while(itt.hasNext())
			{
				i=itt.next();
			 	if(i==item_id)
				{
			 		trans.addAll(rr.transactions);
			 		break;
				}
			}
		}
		
	  return trans;
	}

	public static void main(String[] args) 
	{
		ImprovedApriori apriori=new ImprovedApriori();
	    long start=System.currentTimeMillis();
	    apriori.init();
	    long end=System.currentTimeMillis();
	    System.out.println("\nTime taken to run the Improved Apriori Algorithm= "+(end-start)+"ms");

	}

}
