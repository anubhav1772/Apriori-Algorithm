package com.major.mapreduce.apriori;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class DriverClass 
{
   @SuppressWarnings({"deprecation","unused"})
   public static void main(String args[])
   {
	   try
	   {
	      Configuration conf=new Configuration();
	      String otherArgs[]=new GenericOptionsParser(conf,args).getRemainingArgs();
	      Job job=new Job(conf,"Aprioi Algorithm Implementation");
	      job.setJarByClass(DriverClass.class);
	      
          job.setMapperClass(FrequentItemsetMapper.class);
          job.setReducerClass(FrequentItemsetReducer.class);
          //job.getPartitionerClass();
          job.setPartitionerClass(ReducerAllocator.class);
          //job.setPartitionerClass(Frequent.class);
          
          job.setNumReduceTasks(4);
          
          job.setInputFormatClass(TextInputFormat.class);
          job.setOutputFormatClass(TextOutputFormat.class);
          
          FileInputFormat.addInputPath(job, new Path("groceries.csv"));
          FileOutputFormat.setOutputPath(job, new Path("out1"));
          
          System.exit(job.waitForCompletion(true)?0:1);
          
         
          //job.setOutputKeyClass(Text.class);
          //job.setOutputValueClass(Text.class );
      	      
	   }
	   catch(IOException ioe)
	   {
	     ioe.printStackTrace();
	   }
	   catch(ClassNotFoundException cnfe)
	   {
	      cnfe.printStackTrace();
	   }
	   catch(InterruptedException ine)
	   {
	      ine.printStackTrace();
	   }
	   
   }
}
