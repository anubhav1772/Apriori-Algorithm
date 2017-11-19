package com.major.apriori;
import java.util.HashSet;
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
    
	public static void main(String[] args) 
	{
       Apriori apriori=new Apriori();
       

	}

}
