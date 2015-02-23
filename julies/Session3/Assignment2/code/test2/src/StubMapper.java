import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StubMapper extends Mapper<Object, Text, Text, Text> {

  @Override
  public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {

 	  String[] words = value.toString().split("[ \t]+");
	  for(String word:words)
	  {
		 char[] word2 = word.toCharArray();
		 Arrays.sort(word2);
		 context.write(new Text(new String(word2)), new Text(word));
	  }
  }
}
