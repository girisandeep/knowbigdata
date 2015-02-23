

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StubReducer2 extends Reducer<Text, Text, Text, LongWritable> {

	public void reduce(Text key, Iterable<Text> values, Context context)
		      throws IOException, InterruptedException {
			  
			  List<String> wordlist = new ArrayList<String>();
			  for(Text iw:values)
			  {	  
				  wordlist.add(iw.toString());		  
			  }
			  Collections.sort(wordlist);
			  
			  context.write(key, new LongWritable(0));
			  context.write(new Text(wordlist.get(1)), new LongWritable(new Long(wordlist.get(0).toString())));
		  }
}
