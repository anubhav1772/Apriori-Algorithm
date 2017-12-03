package com.major.tuple;

import java.util.HashSet;
import java.util.Set;

public class Record 
{
	public Set<Integer> itemset;
	public int support;
	public int min;
	public Set<Integer> transactions;
	
	public Record()
	{
		itemset=new HashSet<Integer>();
		support=0;
		min=-1;
		transactions=new HashSet<Integer>();	
	}
	public Record(Set<Integer> itemset,int support,int min,Set<Integer> transactions)
	{
		this.itemset=itemset;
		this.support=support;
		this.min=min;
		this.transactions=transactions;
	}

}
