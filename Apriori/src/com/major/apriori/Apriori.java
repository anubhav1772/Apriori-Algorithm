package com.major.apriori;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.major.tuple.Tuple;

public class Apriori 
{
    Set<Tuple> C;
    Set<Tuple> L;
    int min_support;
    
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
    
	public static void main(String[] args) 
	{
       Apriori apriori=new Apriori();
       

	}

}
