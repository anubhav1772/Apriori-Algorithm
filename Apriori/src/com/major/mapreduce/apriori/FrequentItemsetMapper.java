package com.major.mapreduce.apriori;

import java.io.IOException;

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
   }
}
