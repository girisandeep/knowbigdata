import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StubMapper extends Mapper<Object, Text, Text, LongWritable> {

  @Override
  public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {

    /*
     * TODO implement
     */
	  char[] letters = value.toString().replaceAll("[^a-zA-Z]+", "").toLowerCase().toCharArray();
	  for(char letter:letters)
	  {
		  context.write(new Text(String.valueOf(letter)), new LongWritable(1));
	  }
  }
}
