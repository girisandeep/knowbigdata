import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StubReducer extends Reducer<Text, Text, Text, Text> {

  @Override
  public void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
	  
	  StringBuilder swordlist = new StringBuilder();
	  List<String> wordlist = new ArrayList<String>();
	  String delim = "";
	  for(Text iw:values)
	  {	  
		  if(!wordlist.contains(iw.toString()))
			  wordlist.add(iw.toString());		  
	  }
	  Collections.sort(wordlist);
	  for(String word : wordlist){
		 swordlist.append(delim);
		 swordlist.append(word);
		 delim = ", ";		  
	  }
	  
	  context.write(new Text(""), new Text((swordlist.toString())));
  }
}
