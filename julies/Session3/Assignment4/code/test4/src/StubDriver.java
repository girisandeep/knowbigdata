import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class StubDriver {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: StubDriver <input dir> <output dir>\n");
			System.exit(-1);
		}
		String[] args1 =  {args[0], args[1]};
		String[] args2 = {args[1], args[1] +"2"};
		boolean result = job1(args1);
		System.out.printf("finished job 1");
		job2(args2);
		System.out.printf("finished job 2");
	}
	
	public static boolean job1(String[] args1) throws Exception {	
		if (args1.length != 2) {
			System.out.printf("Usage: StubDriver <input dir> <output dir>\n");
			System.exit(-1);
		}
		JobConf conf = new JobConf();
		Job job = new Job(conf, "votingA");
		job.setJarByClass(StubDriver.class);
		job.setMapperClass(StubMapper.class);
		job.setReducerClass(StubReducer2.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);			
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args1[0]));
		FileOutputFormat.setOutputPath(job, new Path(args1[1]));
		boolean result = job.waitForCompletion(true);
		return result;
		//System.exit(result ? 0 : 1);
	}
	
	public static void job2(String[] args2) throws Exception {
		if (args2.length != 2) {
			System.out.printf("Usage: StubDriver <input dir> <output dir>\n");
			System.exit(-1);
		}
		JobConf conf = new JobConf();
		Job job = new Job(conf, "votingB");
		job.setJarByClass(StubDriver.class);
		job.setMapperClass(StubMapper2.class);
		job.setReducerClass(StubReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args2[0]));
		FileOutputFormat.setOutputPath(job, new Path(args2[1]));
		boolean result = job.waitForCompletion(true);
		//System.exit(result ? 0 : 1);
	}

}
