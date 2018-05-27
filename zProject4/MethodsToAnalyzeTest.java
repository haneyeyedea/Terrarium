import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Tests MethodsToAnalyze - count number of statements in MethodsToAnalyze using various arrays
 * 
 * @author mvail
 * @author melodee
 *
 */
public class MethodsToAnalyzeTest {
	/**
	 * Get an array of specified size and pass it to the specified MethodsToAnalyze method (labeled CHANGEME below)
	 * Report the results.
	 * @param args either size of a new array, an integer to find, an integer to replace or a file containing ints
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//call modified MethodsToAnalyze() with different array sizes and write results to
		// "MethodsToAnalyze.csv" with a PrintStream
		PrintStream outfile = new PrintStream("MethodsToAnalyze.csv");
		outfile.println("size, statements");
		for (int size = 1; size < 101; size += 1) {
			int[] array = ArrayOfInts.randomizedArray(size);
			MethodsToAnalyze.sortIt(array); //CHANGEME to the method we want to analyze
			long statements = MethodsToAnalyze.getStatements();
			outfile.println(size + ", " + statements);

			//summary
			System.out.printf("\nfor n = %d, took %d statements\n",
					size, statements);

			//show "sorted" array
//			for (int i = 0; i < array.length; i++) {
//				System.out.print(array[i] + " ");
//			}
//			System.out.println();
		}
		outfile.close();
	}
}
