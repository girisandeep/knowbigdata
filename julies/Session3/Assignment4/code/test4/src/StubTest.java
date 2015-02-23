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
	MapDriver<Object, Text, Text, LongWritable> mapDriver2;
	ReduceDriver<Text, LongWritable, Text, LongWritable> reduceDriver;
	ReduceDriver<Text, Text, Text, LongWritable> reduceDriver2;
	MapReduceDriver<Object, Text, Text, Text, Text, LongWritable> mapReduceDriver;
	MapReduceDriver<Object, Text, Text, LongWritable, Text, LongWritable> mapReduceDriver2;

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

		StubMapper2 mapper2 = new StubMapper2();
		mapDriver2 = new MapDriver<Object, Text, Text, LongWritable>();
		mapDriver2.setMapper(mapper2);

		
		/*
		 * Set up the reducer test harness.
		 */
		StubReducer reducer = new StubReducer();
		reduceDriver = new ReduceDriver<Text, LongWritable, Text, LongWritable>();
		reduceDriver.setReducer(reducer);
		
		/*
		 * Set up the reducer test harness.
		 */
		StubReducer2 reducer2 = new StubReducer2();
		reduceDriver2 = new ReduceDriver<Text, Text, Text, LongWritable>();
		reduceDriver2.setReducer(reducer2);

		
		/*
		 * Set up the mapper/reducer test harness.
		 */
		mapReduceDriver = new MapReduceDriver<Object, Text, Text, Text, Text, LongWritable>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer2);

		mapReduceDriver2 = new MapReduceDriver<Object, Text, Text, LongWritable, Text, LongWritable>();
		mapReduceDriver2.setMapper(mapper2);
		mapReduceDriver2.setReducer(reducer);		
	}

	/*
	 * Test the mapper.
	 */
	@Test
	public void testMapper() throws IOException {

		mapDriver.setInput(new Pair<Object, Text>(1, new Text(
				"A C")));
		mapDriver.setInput(new Pair<Object, Text>(2, new Text(
				"A 5")));
		List<Pair<Text, Text>> output = mapDriver.run();
		for (Pair<Text, Text> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}

		System.out.println("End of testMapper");
	}
	
	@Test
	public void testMapper2() throws IOException {

		mapDriver2.setInput(new Pair<Object, Text>(1, new Text(
				"C 1")));
		mapDriver2.setInput(new Pair<Object, Text>(2, new Text(
				"C 5")));
		List<Pair<Text, LongWritable>> output = mapDriver2.run();
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}

		System.out.println("End of testMapper2");
	}

	/*
	 * Test the reducer.
	 */
	
	@Test
	public void testReducer() throws IOException {

    	List<LongWritable> mylist3 = new ArrayList<LongWritable>();
		mylist3.add(new LongWritable(5));
		mylist3.add(new LongWritable(1));
		reduceDriver.setInput(new Text("C"), mylist3);		
		List<Pair<Text, LongWritable>> output = reduceDriver.run();
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
		System.out.println("end of reducer test");
	}

	
	/*
	 * Test the reducer.
	 */
	@Test
	public void testReducer2() throws IOException {

		List<Text> mylist = new ArrayList<Text>();
		mylist.add(new Text("C"));
		mylist.add(new Text("5"));
		reduceDriver2.setInput(new Text("A"), mylist);
		List<Pair<Text,LongWritable>> output = reduceDriver2.run();
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
		System.out.println("end of reducer2 test");
	}

	/*
	 * Test the mapper and reducer working together.
	 */
	@Test
	public void testMapReduce() throws IOException {

		mapReduceDriver.addInput(new Pair<Object, Text>(1, new Text(
				"A C")));
		mapReduceDriver.addInput(new Pair<Object, Text>(2, new Text(
				"B C")));
		mapReduceDriver.addInput(new Pair<Object, Text>(3, new Text(
				"C F")));
		mapReduceDriver.addInput(new Pair<Object, Text>(4, new Text(
				"A 5")));		
		mapReduceDriver.addInput(new Pair<Object, Text>(5, new Text(
				"B 1")));
		mapReduceDriver.addInput(new Pair<Object, Text>(6, new Text(
				"C 11")));
		List<Pair<Text,LongWritable>> output = mapReduceDriver.run();
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
		System.out.println("end of mapreduce1 test");
	}

	@Test
	public void testMapReduce2() throws IOException {

		mapReduceDriver2.addInput(new Pair<Object, Text>(1, new Text(
				"A 0")));
		mapReduceDriver2.addInput(new Pair<Object, Text>(2, new Text(
				"C 5")));
		mapReduceDriver2.addInput(new Pair<Object, Text>(3, new Text(
				"B 0")));
		mapReduceDriver2.addInput(new Pair<Object, Text>(4, new Text(
				"C 1")));		
		mapReduceDriver2.addInput(new Pair<Object, Text>(5, new Text(
				"C 0")));
		mapReduceDriver2.addInput(new Pair<Object, Text>(6, new Text(
				"F 11")));
		List<Pair<Text,LongWritable>> output = mapReduceDriver2.run();
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
		System.out.println("end of mapreduce2 test");
		
	}

}
