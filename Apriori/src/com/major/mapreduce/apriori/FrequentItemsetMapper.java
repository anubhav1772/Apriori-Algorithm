package com.major.mapreduce.apriori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import javax.naming.Context;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapOutputCollector.Context;
//import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapreduce.Mapper;

public class FrequentItemsetMapper extends  Mapper<LongWritable, Text, Text, IntWritable>
{
   public void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException
   {
	   String itemset=value.toString(); //itemset of a particular transaction
	   List<String> power_set=findAllPossibleItemsets(itemset.split(" "));
	   for(String item_set:power_set)
	   {
		   context.write(new Text(item_set),new IntWritable(1));
	   }
   }
   
   public List<String> findAllPossibleItemsets(String items[])
   {
	   int n=items.length;
	   List<String> itemsets=new ArrayList<String>();
	   for(int i=0;i<Math.pow(2,n);i++) // Math.pow(2,n)=(1<<n)
	   {
		   List<String> list=new ArrayList<String>(n);
		   for(int j=0;j<n;j++)
		   {
			   if((i&(1<<j))>0)
			   {
				   list.add(items[j]);
			   }
		   }
		   if(list.size()>0)
		   {
			   itemsets.add(list.toString());
		   }
	   }
	   return itemsets;
   }
}
