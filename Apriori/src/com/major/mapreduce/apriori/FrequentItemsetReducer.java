package com.major.mapreduce.apriori;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FrequentItemsetReducer extends Reducer<Text,IntWritable,Text,Text> 
{
	public void reduce(Text key,Iterable<IntWritable> values,Context context)throws IOException,InterruptedException
	{
		int count=0;
		for(IntWritable num:values)
		{
			count+=num.get();
		}
		context.write(key,new Text(Integer.toString(count)));
	}

}
