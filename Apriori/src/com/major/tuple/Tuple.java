package com.major.tuple;

import java.util.HashSet;
import java.util.Set;

public class Tuple 
{
	Set<Integer> itemset;
	int support;
	public Tuple()
	{
		itemset=new HashSet<Integer>();
		support=0;	
	}
	
}
