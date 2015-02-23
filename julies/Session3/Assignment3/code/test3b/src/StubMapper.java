import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StubMapper extends Mapper<Object, Text, Text, Text> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] words = value.toString().split("[ \t]+");
		
		List<String> words2 = new ArrayList<String>();
		words2.add(words[1]);
		words2.add(new StringBuilder(words[1]).reverse().toString());
		
		int result = words2.get(0).compareTo(words2.get(1));
		
		if (result > 0) {
			context.write(new Text(words2.get(1)), new Text(words[0]));
		} else {
			context.write(new Text(words2.get(0)), new Text(words[0]));
		}
	}
}
