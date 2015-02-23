import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

public class StubTest {

	/*
	 * Declare harnesses that let you test a mapper, a reducer, and a mapper and
	 * a reducer working together.
	 */
	MapDriver<Object, Text, Text, Text> mapDriver;
	ReduceDriver<Text, Text, Text, Text> reduceDriver;
	MapReduceDriver<Object, Text, Text, Text, Text, Text> mapReduceDriver;

	/*
	 * Set up the test. This method will be called before every test.
	 */
	@Before
	public void setUp() {

		/*
		 * Set up the mapper test harness.
		 */
		StubMapper mapper = new StubMapper();
		mapDriver = new MapDriver<Object, Text, Text, Text>();
		mapDriver.setMapper(mapper);

		/*
		 * Set up the reducer test harness.
		 */
		StubReducer reducer = new StubReducer();
		reduceDriver = new ReduceDriver<Text, Text, Text, Text>();
		reduceDriver.setReducer(reducer);

		/*
		 * Set up the mapper/reducer test harness.
		 */
		mapReduceDriver = new MapReduceDriver<Object, Text, Text, Text, Text, Text>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	}

	/*
	 * Test the mapper.
	 */
	@Test
	public void testMapper() throws IOException {

		mapDriver.setInput(new Pair<Object, Text>(1, new Text(
				"the cat act in tic tac toe")));
		List<Pair<Text, Text>> output = mapDriver.run();
		for (Pair<Text, Text> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
		
		System.out.println("End of testMapper");

	}

	/*
	 * Test the reducer.
	 */
	@Test
	public void testReducer() throws IOException {

		List<Text> mylist = new ArrayList<Text>();
		mylist.add(new Text("act"));
		mylist.add(new Text("cat"));
		mylist.add(new Text("tac"));
		reduceDriver.setInput(new Text("act"), mylist);		
		List<Pair<Text, Text>> output = reduceDriver.run();
		for (Pair<Text, Text> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
		System.out.println("end of reducer test");
	}

	/*
	 * Test the mapper and reducer working together.
	 */
	@Test
	public void testMapReduce() throws IOException {

		mapReduceDriver.addInput(new Pair<Object, Text>(1, new Text(
				"the cat act in tic tac toe")));
		mapReduceDriver.addInput(new Pair<Object, Text>(2, new Text(
				"toe eot eto this that the")));
		List<Pair<Text, Text>> output = mapReduceDriver.run();
		for (Pair<Text, Text> p : output) {
			System.out.println(p.getSecond());
		}
	}
}
