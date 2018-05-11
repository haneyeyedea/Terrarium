import java.awt.Point;
import java.util.ArrayList;

/**
 * Test RandomWalk functionality.
 * 
 * @author mvail
 * @author marissa
 */
public class RandomWalkUnitTester
{
	private static long seed;
	private static int gridSize;
	private static Point start;
	private static Point end;
	private static int minSteps;
	private static int maxSteps;
	
	private static int status = 0;
	
	/**
	 * @param args required int gridSize
	 */
	public static void main(String[] args)
	{
		gridSize = 10;
		seed = 1234;
		
		start = new Point(0, gridSize - 1);
		end = new Point(gridSize - 1, 0);
		
		minSteps = 2*(gridSize-1);
		maxSteps = gridSize*gridSize;
		
		testUnseededPathConstructor();
		testSeededPathConstructor();
		
		testStep();
		testStepAfterDone();
		testUnseededStep();
		testSeededStep();
		
		testCreateWalk();
		testSeededCreateWalk();
		testUnseededCreateWalk();
		
		testGetPath();
		
		System.exit(status);
	}
	
	private static void testUnseededPathConstructor()
	{
		String test = "testUnseededPathConstructor - RandomWalk(" + gridSize + ")";
		try
		{
			RandomWalk walk = new RandomWalk(gridSize);
			pass(test);
		
			test = "testUnseededPathConstructor - isDone";
			testIsDone(test, walk, false);
			
			test = "testUnseededPathConstructor - getGridSize";
			int expectedSize = gridSize;
			int actualSize = walk.getGridSize();
			if(expectedSize == actualSize)
			{
				pass(test);
			}
			else
			{
				fail(test, "Grid size should be " + expectedSize, "Grid size is " + actualSize);
			}
			
			test = "testUnseededPathConstructor - getStartPoint";
			testStartPoint(test, walk, start);
			
			test = "testUnseededPathConstructor - getEndPoint";
			testEndPoint(test, walk, end);
			
			test = "testUnseededPathConstructor - getCurrentPoint";
			testCurrentPoint(test, walk, start);
			
			test = "testUnseededPathConstructor - getPath";
			ArrayList<Point> path = walk.getPath();
			if(path == null)
			{
				fail(test, "Path should be instantiated.", null);
			}
			expectedSize = 1;
			actualSize = path.size();
			if(expectedSize == actualSize)
			{
				pass(test);
			}
			else
			{
				fail(test, "Path size should be " + expectedSize, "Path size is " + actualSize);
			}
		}
		catch (Exception e)
		{
			fail(test, "Exception in constructor. " + e.getStackTrace());
		}
	}
	
	private static void testSeededPathConstructor()
	{
		String test = "testSeededPathConstructor - RandomWalk(" + gridSize + ", " + seed + ")";
		try
		{
			RandomWalk walk = new RandomWalk(gridSize, seed);
			pass(test);
		
			test = "testSeededPathConstructor - isDone";
			testIsDone(test, walk, false);
			
			test = "testSeededPathConstructor - getGridSize";
			int expectedSize = gridSize;
			int actualSize = walk.getGridSize();
			if(expectedSize == actualSize)
			{
				pass(test);
			}
			else
			{
				fail(test, "Grid size should be " + expectedSize, "Grid size is " + actualSize);
			}
			
			test = "testSeededPathConstructor - getStartPoint";
			testStartPoint(test, walk, start);
			
			test = "testSeededPathConstructor - getEndPoint";
			testEndPoint(test, walk, end);
			
			test = "testSeededPathConstructor - getCurrentPoint";
			testCurrentPoint(test, walk, start);
			
			test = "testSeededPathConstructor - getPath";
			ArrayList<Point> path = walk.getPath();
			if(path == null)
			{
				fail(test, "Path should be instantiated.", null);
			}
			expectedSize = 1;
			actualSize = path.size();
			if(expectedSize == actualSize)
			{
				pass(test);
			}
			else
			{
				fail(test, "Path size should be " + expectedSize, "Path size is " + actualSize);
			}
		}
		catch (Exception e)
		{
			fail(test, "Exception in constructor. " + e.getStackTrace());
		}
	}
	
	/**
	 * Verify correct functionality of RandomWalk when using step() to build
	 * the path.
	 */
	private static void testGetPath()
	{
		RandomWalk walk = new RandomWalk(gridSize);
		
		ArrayList<Point> path = walk.getPath();
		int originalSize = path.size();
		
		Point toAdd = new Point(-1000, -999);
		
		path.add(toAdd);
		
		int newSize = walk.getPath().size();
		
		if(originalSize == newSize)
		{
			pass("testGetPath");
		}
		else
		{
			fail("testGetPath", "Enforces encapsulation by returning copy of path.", "Violated encapsulation. Successfully added point to path without calling step()");
		}
		
	}
	
	/**
	 * Verify correct functionality of RandomWalk when using step() to build
	 * the path.
	 */
	private static void testStep()
	{
		RandomWalk walk = new RandomWalk(gridSize);
		
		int count = 0;
		while (!walk.isDone() && count < maxSteps) {
			walk.step();
			count++;
		}
		String test = "step - maxSteps";
		if (count >= maxSteps) {
			fail(test, "Finish before " + maxSteps + "steps.", "Exceeded max number of steps.");
		}
		
		test = "step - isDone";
		testIsDone(test, walk, true);
		
		test = "step - getStartPoint";
		testStartPoint(test, walk, start);
		
		test = "step - getEndPoint";
		testEndPoint(test, walk, end);
		
		test = "step - checkValidPath";
		checkValidPath(test, walk);
		
		test = "step - toString";
		testToString(test, walk);
	}
	
	
	/**
	 * check to see if calling step() adds invalid points
	 * @param walk
	 * @return
	 */
	private static void testStepAfterDone()
	{
		RandomWalk walk = new RandomWalk(gridSize);

		while(!walk.isDone())
		{
			walk.step();
		}
		int pathSize = walk.getPath().size();
		
		walk.step();
		
		if (pathSize == walk.getPath().size()) {
			pass("testStepAfterDone");
		} else {
			fail("testStepAfterDone", "Failed: Invalid points added. " + printPath(walk));
		}
	}
	

	/**
	 * Confirm that results are identical when seed is the same.
	 */
	private static void testSeededStep()
	{
		String test = "testSeededStep - compare paths";
		try
		{
			RandomWalk walk1 = new RandomWalk(gridSize, seed);
			RandomWalk walk2 = new RandomWalk(gridSize, seed);
			
			int count = 0;
			while (!walk1.isDone() && count < maxSteps) {
				walk1.step();
				count++;
			}
			
			count = 0;
			while (!walk2.isDone() && count < maxSteps) {
				walk2.step();
				count++;
			}
			
			if (checkSamePaths(walk1.getPath(), walk2.getPath()))
			{
				pass(test);
			}
			else
			{
				fail(test, "Expected same path.", "Paths differ: \n Path 1: " + printPath(walk1) + "\n Path 2: " + printPath(walk2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Confirm that results are different when no seed is specified.
	 */
	private static void testUnseededStep()
	{
		
		String test = "testUnseededStep - compare paths";
		try
		{
			RandomWalk walk1 = new RandomWalk(gridSize);
			RandomWalk walk2 = new RandomWalk(gridSize);
			
			int count = 0;
			while (!walk1.isDone() && count < maxSteps) {
				walk1.step();
				count++;
			}
			
			count = 0;
			while (!walk2.isDone() && count < maxSteps) {
				walk2.step();
				count++;
			}
			
			if (checkSamePaths(walk1.getPath(), walk2.getPath()))
			{
				fail(test, "Expected different paths.", "Paths are the same: \n Path 1: " + printPath(walk1) + "\n Path 2: " + printPath(walk2));
			}
			else
			{
				pass(test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Verify correct functionality of RandomWalk when using createWalk() to build
	 * the path.
	 */
	private static void testCreateWalk()
	{
		RandomWalk walk = new RandomWalk(gridSize);
		
		walk.createWalk();
		
		String test = "createWalk - isDone";
		testIsDone(test, walk, true);
		
		test = "createWalk - getStartPoint";
		testStartPoint(test, walk, start);
		
		test = "createWalk - getEndPoint";
		testEndPoint(test, walk, end);
		
		test = "createWalk - checkValidPath";
		checkValidPath(test, walk);
		
		test = "createWalk - toString";
		testToString(test, walk);
	}
	
	/**
	 * Confirm that results are identical when seed is the same.
	 */
	private static void testSeededCreateWalk()
	{
		String test = "testSeededCreateWalk - compare paths";
		try
		{
			RandomWalk walk1 = new RandomWalk(gridSize, seed);
			RandomWalk walk2 = new RandomWalk(gridSize, seed);
			
			walk1.createWalk();
			walk2.createWalk();
			
			if (checkSamePaths(walk1.getPath(), walk2.getPath()))
			{
				pass(test);
			}
			else
			{
				fail(test, "Expected same path.", "Paths differ: \n Path 1: " + printPath(walk1) + "\n Path 2: " + printPath(walk2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Confirm that results are different when no seed is specified.
	 */
	private static void testUnseededCreateWalk()
	{
		
		String test = "testUnseededCreateWalk - compare paths";
		try
		{
			RandomWalk walk1 = new RandomWalk(gridSize);
			RandomWalk walk2 = new RandomWalk(gridSize);
			
			walk1.createWalk();
			walk2.createWalk();
			
			if (checkSamePaths(walk1.getPath(), walk2.getPath()))
			{
				fail(test, "Expected different paths.", "Paths are the same: \n Path 1: " + printPath(walk1) + "\n Path 2: " + printPath(walk2));
			}
			else
			{
				pass(test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Display path in preferred format (wrapping not required)
	 * @param path Points to print
	 */
	private static void testToString(String testName, RandomWalk walk)
	{
		String expected = printPath(walk);
		String actual = walk.toString();
		if(equalsIgnoreWhitespace(actual, expected))
		{
			pass(testName);
		}
		else
		{
			fail(testName, "\n" + expected, "\n" + actual);
		}
	}
	
	private static String printPath(RandomWalk walk)
	{
		StringBuilder str = new StringBuilder();
		for (Point p : walk.getPath())
		{
			str.append("[");
			str.append(p.x);
			str.append(",");
			str.append(p.y);
			str.append("] ");
		}
		return str.toString();
	}

	/**
	 * test if path is valid
	 * @param path
	 * @param dim
	 * @return
	 */
	private static void checkValidPath(String testName, RandomWalk walk)
	{
		ArrayList<Point> path = walk.getPath();
		
		String test = testName + " - check path length";
		if (path.size() > minSteps && path.size() <= maxSteps)
		{
			pass(test);
		}
		else
		{
			fail(test, "Path length between " + minSteps + " and " + maxSteps + ".", "Path length was " + path.size());
		}
		
		test = testName + " - check first and last points";
		Point pointZero = path.get(0);
		if (start.equals(pointZero))
		{
			pass(test);
		}
		else
		{
			fail(test, "First point of path should be (" + start.x + ", " + start.y + ")", 
					"First point of path is (" + pointZero.x + ", " + pointZero.y + ")");
		}
		Point pointLast = path.get(path.size()-1);
		if (end.equals(pointLast))
		{
			pass(test);
		}
		else
		{
			fail(test, "Last point of path should be (" + end.x + ", " + end.y + ")", 
					"Last point of path is (" + pointLast.x + ", " + pointLast.y + ")");
		}
		
		test = testName + " - validate steps";
		boolean pass = true;
		for (int i = 1; i < path.size(); i++) {
			Point p0 = path.get(i-1);
			Point p1 = path.get(i);
			int diff = Math.abs(p1.x - p0.x) + Math.abs(p1.y - p0.y);
			if (diff != 1) {
				fail(test, "Invalid step: (" + p0.x + "," + p0.y + ") to (" + p1.x + "," + p1.y + ")");
				pass = false;
			}
		}
		if(pass)
		{
			pass(test);
		}
	}

	/**
	 * compare paths for same contents
	 * @param path1
	 * @param path2
	 * @return
	 */
	private static boolean checkSamePaths(ArrayList<Point> path1, ArrayList<Point> path2)
	{
		if (path1.size() != path2.size())
		{
			fail("checkSamePaths - size", "Paths should be same length.", 
					"Path 1 length: " + path1.size() + ", Path 2 length: " + path2.size());
			return false;
		}
		else
		{
			for (int i = 0; i < path1.size(); i++)
			{
				if (!path1.get(i).equals(path2.get(i)))
				{
					return false;
				}
			}
			return true;
		}
	}
	
	
	
	/**
	 * Check if RandomWalk's isDone() returns the expected value
	 * @param walk
	 * @param expected
	 * @return
	 */
	private static void testIsDone(String testName, RandomWalk walk, boolean expected) {
		
		boolean actual = walk.isDone();
		if(expected == actual)
		{
			pass(testName);
		}
		else
		{
			fail(testName, "isDone should be " + expected, "isDone is " + actual);
		}
	}
	
	
	/**
	 * Check if RandomWalk's isDone() returns the expected value
	 * @param walk
	 * @param expected
	 * @return
	 */
	private static void testStartPoint(String testName, RandomWalk walk, Point expected) {
		
		Point actualPoint = walk.getStartPoint();
		if(expected.equals(actualPoint))
		{
			pass(testName);
		}
		else
		{
			fail(testName, "Start point should be " + expected, "Start point is " + actualPoint);
		}
	}
	
	
	/**
	 * Check if RandomWalk's isDone() returns the expected value
	 * @param walk
	 * @param expected
	 * @return
	 */
	private static void testEndPoint(String testName, RandomWalk walk, Point expected) {
		
		Point actualPoint = walk.getEndPoint();
		if(expected.equals(actualPoint))
		{
			pass(testName);
		}
		else
		{
			fail(testName, "End point should be " + expected, "End point is " + actualPoint);
		}
	}
	
	/**
	 * Check if RandomWalk's isDone() returns the expected value
	 * @param walk
	 * @param expected
	 * @return
	 */
	private static void testCurrentPoint(String testName, RandomWalk walk, Point expected) {
		
		Point actualPoint = walk.getCurrentPoint();
		if(expected.equals(actualPoint))
		{
			pass(testName);
		}
		else
		{
			fail(testName, "Current point should be " + expected, "Current point is " + actualPoint);
		}
	}
	
	private static void fail(String testName, String expected, String actual)
	{
		System.err.println("FAILED: " + testName);
		System.err.println("    --> expected: " + expected);
		System.err.println("    -->   actual: " + actual);
		status = 1;
	}
	
	private static void fail(String testName, String message)
	{
		System.err.println("FAILED: " + testName);
		System.err.println("    -->: " + message);
		status = 1;
	}

	private static void pass(String testName)
	{
		System.out.println("PASSED: " + testName);
	}
	
	
	private static boolean equalsIgnoreWhitespace(String one, String two)
	{
		return one.replaceAll("\\s+","").equalsIgnoreCase(two.replaceAll("\\s+",""));
	}
}
