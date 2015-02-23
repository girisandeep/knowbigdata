import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StubMapper extends Mapper<Object, Text, Text, Text> {

  @Override
  public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {

    /*
     * TODO implement
     */
	  String[] words = value.toString().split("[ \t]+");
	  context.write(new Text(words[1]), new Text(words[0]));
  }
}
