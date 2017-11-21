package com.major.tuple;

import java.util.HashSet;
import java.util.Set;

public class Tuple 
{
	public Set<Integer> itemset;
	public int support;
	public Tuple()
	{
		itemset=new HashSet<Integer>();
		support=0;	
	}
	public Tuple(Set itemset,int support)
	{
		this.itemset=itemset;
		this.support=support;
	}
	
}
