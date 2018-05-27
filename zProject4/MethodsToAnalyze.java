/**
 * A collection of methods that work with arrays of ints.
 * 
 * @author mvail
 */
public class MethodsToAnalyze {
	
	//NEW VARIABLE ADDED FOR DATA COLLECTION
	private static long numStatements;

	/**
	 * Return index where value is found in array or -1 if not found.
	 * @param array ints where value may be found
	 * @param value int that may be in array
	 * @return index where value is found or -1 if not found
	 */
	public static int find(int[] array, int value) {
		
		//ADDED FOR DATA COLLECTION
		numStatements += 2; //for-loop init and 1st for-condition check
		
		for (int i = 0; i < array.length; i++) {
			
			//ADDED FOR DATA COLLECTION
			numStatements += 3; //if-condition check, increment of for-loop and for-condition check
			
			if (array[i] == value) {
				
				//ADDED FOR DATA COLLECTION
				numStatements += 1; //returning index
				
				return i;
			}
		}
		
		//ADDED FOR DATA COLLECTION
		numStatements += 1; //returning index
		
		return -1;
	}

	/**
	 * Replace all occurrences of oldValue with newValue in array.
	 * @param array ints where oldValue may be found
	 * @param oldValue value to replace
	 * @param newValue new value
	 */
	public static void replaceAll(int[] array, int oldValue, int newValue) {
		
		//ADDED FOR DATA COLLECTION
		numStatements = 0; //initialize before calling find()
				
		int index = find(array, oldValue);
		
		//ADDED FOR DATA COLLECTION
		numStatements += 1; //for 1st condition check
		
		while (index > -1) {
			
			//ADDED FOR DATA COLLECTION
			numStatements += 2; //assignment and next condition check
			
			array[index] = newValue;
			index = find(array, oldValue);
		}
	}
	
	/**
	 * Take an int[] and reorganize it so they are in ascending order.
	 * @param array ints that need to be ordered 
	 */
	public static void sortIt(int[] array) {
		
		//ADDED FOR DATA COLLECTION
		numStatements = 2; //for loop init and 1st condition check
				
		for (int next = 1; next < array.length; next++) {
			
			//ADDED FOR DATA COLLECTION
			numStatements += 6; //for 3 assignments, inner condition check, increment, and outer condition check
			
			int value = array[next];
			int index = next;
			while (index > 0 && value < array[index - 1]) {
				
				//ADDED FOR DATA COLLECTION
				numStatements += 3; //for 1 assignment, increment, and condition check
				
				array[index] = array[index - 1];
				index--;
			}
			array[index] = value;
		}
	}
	
	public static long getStatements() {
		return numStatements;
	}
}
