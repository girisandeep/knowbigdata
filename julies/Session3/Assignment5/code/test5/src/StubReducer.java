import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry; 

public class StubReducer extends Reducer<Text, Text, Text, Text> {

  @Override
  public void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
	  
	  Map<String, Integer> myMap = new HashMap<String, Integer>();
	  for(Text iw:values)
	  {
		  if(myMap.containsKey(iw)){
			  Integer sum = myMap.get(iw);
			  sum = sum + 1;
			  myMap.put(iw.toString(), sum);
		  } else {
			  myMap.put(iw.toString(), 1);
		  }
	  }
      Set<Entry<String, Integer>> set = myMap.entrySet();
      List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
      Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
      {
          public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
          {
              return (o2.getValue()).compareTo( o1.getValue() );
          }
      } );
      
      StringBuffer wordlist = new StringBuffer();
      int cnt = 1;
      String delim = "";
      
      for(Map.Entry<String, Integer> entry:list){
    	  if (cnt > 5){
    		  break;
    	  }
    	  wordlist.append(delim);
    	  wordlist.append(entry.getKey());
    	  delim = ", ";
    	  cnt++;
      }
	  context.write(key, new Text(wordlist.toString()));
  }
}
