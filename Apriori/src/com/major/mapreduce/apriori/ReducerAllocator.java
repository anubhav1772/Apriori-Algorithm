package com.major.mapreduce.apriori;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapreduce.Partitioner;

public class ReducerAllocator extends Partitioner<Text,IntWritable>
{
    public int getPartition(Text key,IntWritable value,int numReduceTasks)
    {
    	int n=key.toString().replace("[","").replace("]","").split(",").length;
        if(n==1)
        {
        	return 0;
        }
        else if(n==2)
        {
        	return 1;
        }
        else
        {
        	return 2;
        }
    
    }
}