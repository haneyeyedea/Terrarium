import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Tests all data files entered in command line to see if it exists and to verify it is in the correct formatting for a grid of numbers.
 * The correct formatting is as follows: 
 * 	1. The first line of the code contains 2 integers which represent the number of rows and columns of the ensuing grid, respectively
 * 	2. The grid of numbers are separated by only one space, and no spaces after the row is complete
 * 	3. The numbers in the grid are doubles
 * 
 * @throws NumberFormatException
 * @throws InvalidFileFormatException
 * @throws FileNotFoundException
 * @throws InputMismatchException
 * @throws NoSuchElementException
 * 
 * @author melodee
 *
 */
public class FormatChecker {

	public static void main (String[] args) {
		
		if (args.length < 1) {
			System.err.println("Usage: java ProcessText file1 [file2 ...]");
			System.exit(1);
		}
		
		
		for (int i=0; i < args.length; i++) {
			File file = new File(args[i]);
			int firstDim = 0;
			int secondDim = 0;
			
			System.out.println(file);
			Scanner fileScan = null;
			
			try {
				fileScan = new Scanner(file);
				
				String line;
				int lineCount = 0;
				int dimCount = 0;
				
				while (fileScan.hasNextLine()) {
					line = fileScan.nextLine();
					
					double w = 0.0;
					int x = 0;
					String y = "";
					boolean z = false;
					int secondCount = 0;
					
					
					if (lineCount == 0 ) {
						for(int j = 0; j < line.length(); j++) {
							if (line.charAt(j)!= ' ') {
								y += line.charAt(j);
								z = false;
							}
							else {
								if (z) {throw new InvalidFileFormatException ("Using more than one space in line " + (lineCount + 1));}
								x = Integer.parseInt(y);
								y = "";
								z = true;
								
								if(dimCount == 0) {firstDim = x;}
								else {throw new InvalidFileFormatException("Wrong format in Line 1");}
								
								dimCount++;
							}
							if(j == line.length()-1) {
								if (z) {throw new InvalidFileFormatException ("Using more than one space in line " + (lineCount + 1));}
								x = Integer.parseInt(y);
								
								if (dimCount == 1) {secondDim = x;}
								else {throw new InvalidFileFormatException("Too many arguments in Line 1");}
							}
						}
								
						if(firstDim <= 0 || secondDim <= 0) {throw new InvalidFileFormatException ("First line must give 2 dimensions");}
					}
					else {
						for (int j = 0; j < line.length(); j++) {
							if (line.charAt(j) != ' ') {
								y += line.charAt(j);
								z = false;
							}
							else {
								if (z) {throw new InvalidFileFormatException ("Using more than one space in line " + (lineCount + 1));}
								w = Double.parseDouble(y);
								y = "";
								z = true;
								
								secondCount++;
							}
							if (j == line.length()-1) {
								if (z) {throw new InvalidFileFormatException ("Using more than one space in line " + (lineCount + 1));}
								w = Double.parseDouble(y);
								secondCount++;
								
								if (secondCount != secondDim) {throw new InvalidFileFormatException ("Incorrect number of data elements in Line " + (lineCount+1));}
							}
						}
					}
					
					if (lineCount >= firstDim+1) {throw new InvalidFileFormatException ("More data rows than expected");}
					lineCount++;
				}
				
				fileScan.close();
				
				System.out.println("Valid");
			}
			catch(InvalidFileFormatException e) {
				System.out.println(e.toString());
				System.out.println("Invalid");
				fileScan.close();
			}
			catch(FileNotFoundException e) {
				System.out.println(e.toString());
				System.out.println("Invalid");
			}
			catch(InputMismatchException e) {
				System.out.println(e.toString());
				System.out.println("Invalid");
				fileScan.close();
			}
			catch(NumberFormatException e){
				System.out.println(e.toString());
				System.out.println("Invalid");
				fileScan.close();
			}
			catch(NoSuchElementException e) {
				System.out.println(e.toString());
				System.out.println("Invalid");
				fileScan.close();
			}
			finally {
				System.out.println("");
			}
		}
	}
}
