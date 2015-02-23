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
	  
	  for(int i = 0; i < words.length; i++){
		  if(i + 1 == words.length){
			  //do nothing	  
		  } else {
			  context.write(new Text(words[i].replaceAll("[^a-zA-Z]+", "").toLowerCase()), new Text(words[i+1].replaceAll("[^a-zA-Z]+", "").toLowerCase()));
		  }
	  } 
  }
}
