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

		/*
		 * For this test, the mapper's input will be "1 cat cat dog" TODO:
		 * implement
		 */
		mapDriver.setInput(new Pair<Object, Text>(1, new Text(
				"User1 ACGT")));
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

		/*
		 * For this test, the reducer's input will be "cat 1 1". The expected
		 * output is "cat 2". TODO: implement
		 */
		List<Text> mylist = new ArrayList<Text>();
		mylist.add(new Text("User1"));
		mylist.add(new Text("User4"));
		reduceDriver.setInput(new Text("ACGT"), mylist);
		List<Pair<Text, Text>> output = reduceDriver.run();
		for (Pair<Text, Text> p : output) {
			System.out.println(p.getSecond());
		}
		System.out.println("end of reducer test");


	}

	/*
	 * Test the mapper and reducer working together.
	 */
	@Test
	public void testMapReduce() throws IOException {

		mapReduceDriver.addInput(new Pair<Object, Text>(1, new Text(
				"User1 ACGT")));
		mapReduceDriver.addInput(new Pair<Object, Text>(2, new Text(
				"User2 TGCA")));
		mapReduceDriver.addInput(new Pair<Object, Text>(3, new Text(
				"User3 ACG")));
		mapReduceDriver.addInput(new Pair<Object, Text>(4, new Text(
				"User4 ACGT")));		
		mapReduceDriver.addInput(new Pair<Object, Text>(5, new Text(
				"User5 ACG")));
		mapReduceDriver.addInput(new Pair<Object, Text>(6, new Text(
				"User6 AGCT")));		
		List<Pair<Text, Text>> output = mapReduceDriver.run();
		for (Pair<Text, Text> p : output) {
			System.out.println(p.getSecond());
		}
	}
}
