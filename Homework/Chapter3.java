import java.util.Random;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Chapter3 
{
	
	enum Days {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday}

	public static void main(String[] args) {
		
		// Ex 3.2
		// Write a statement that prints the eighth character of a String object
		// called introduction.
		
		System.out.println("Problem 1:");
		String introduction = new String("Atticus and Apollo");
		
		System.out.println("The eighth charachter of \"" + introduction + "\" is \'" + introduction.charAt(9) + "\'");

		
		// Ex 3.3
		// Write a declaration for a String variable called change and initialize it to the characters stored in 
		// another String object called original with all 'e' characters changed to 'j'.
		
		System.out.println("\nProblem 2:");
		String original = new String("Change is good");
		
		String change = original.replace('e', 'j');
		System.out.println("New change is now \"" + change + "\"");
		
		
		// Ex 3.4
		// What output is produced by the following code fragment?
		// String m1, m2, m3;
		// m1 = "Quest for the Holy Grail";
		// m2 = m1.toLowerCase();
		// m3 = m1 + " " + m2;
		// System.out.println(m3.replace('h', 'z'));
		
		System.out.println("\nProblem 3: ");
		System.out.println("New string is: \"Quest for tze Holy Grail quest for tze zoly grail");
		
		
		// Ex 3.6
		//Assuming that a Random object called generator has been created, what is the range of the result of each of 
		// the following expressions?
		// a. generator.nextInt(20)
		// b. generator.nextInt(8) + 1
		// c. generator.nextInt(45) + 10
		// d. generator.nextInt(100) - 50
		
		System.out.println("\nProblem 4:");
		
		System.out.println("a) [0, 20)\nb) [1, 9)\nc) [10, 55)\nd) [-50, 50)");
		
		
		// Ex 3.7
		// Write code to declare and instantiate an object of the Random class (call the object reference variable 
		// rand). Then write a list of expressions using the nextInt method that generates random numbers in the 
		// specified ranges, including the end points. Use the version of the nextInt method that accepts a single
		// integer parameter.
		// a. 0 to 10
		// b. 0 to 500
		// c. 1 to 10
		// d. 1 to 500
		// e. 25 to 50
		// f. -10 to 15
		
		System.out.println("\nProblem 5:");
		
		// "import java.util.Random; inserted before class declaration
		
		Random rand = new Random();
		
		int a = rand.nextInt(11);
		int b = rand.nextInt(501);
		int c = rand.nextInt(10) + 1;
		int d = rand.nextInt(500) + 1;
		int e = rand.nextInt(25) + 25;
		int f = rand.nextInt(25) - 10;
		
		System.out.println("A sample from each range:\na) " +a+ "\nb) " +b+ "\nc) " +c+ "\nd) " +d+ "\ne) " +e+ "\nf) " +f);
		
		
		// Ex 3.11
		// Write code statements that prompt for and read a double value from the user and then print the result of raising 
		// that value to the fourth power. Output the results to three decimal places.
		
		System.out.println("\nProblem 6:");
		
		// "import java.util.Scanner;" and "import java.text.DecimalFormat;" inserted before class declaration
		
		double number, calculate;
		
		Scanner scan = new Scanner(System.in);
		DecimalFormat fmt = new DecimalFormat("0.###");
		
		System.out.print("Enter a number: ");
		number = scan.nextDouble();
		
		calculate = Math.pow(number, 4);
		System.out.println(fmt.format(number) + " raised to the fourth power is " + fmt.format(calculate));
		
		
		// Ex 3.12
		// Write a declaration for an enumerated type that represents the days of the week
		
		System.out.println("\nProblem 7:");
		
		// enum Days {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday} inserted before main 
		
		Days first, second, third, fourth, fifth, sixth, seventh, eighth;
		first = Days.Sunday;
		second = Days.Monday;
		third = Days.Tuesday;
		fourth = Days.Wednesday;
		fifth = Days.Thursday;
		sixth = Days.Friday;
		seventh = Days.Saturday;
		
		System.out.print("The first day of the week is " +first+ "; second is " +second+ "; third is " +third+ "; fourth is ");
		System.out.println(fourth+ "; Fifth is " + fifth+ "; Sixth is " +sixth+ "; Seventh is " +seventh);
		
		
		// PP 3.2
		// Write an application that prints the sum of cubes. Prompt for and read two integer values and print the sum 
		// of each value raised to the third power.
		
		System.out.println("\nProblem 8:");
		
		int number1, number2;
		double cube1, cube2;
		
		System.out.print("Enter the first number: ");
		number1 = scan.nextInt();
		System.out.print("Enter the second number: ");
		number2 = scan.nextInt();
		
		cube1 = Math.pow(number1, 3);
		cube2 = Math.pow(number2, 3);
		
		System.out.println("The sum of the cubes of the two numbers is " + (cube1 + cube2));
		
		
		// PP 3.3
		// Write an application that creates and prints a random phone number of the form XXX-XXX-XXXX. Include the dashes in
		// the output. Do not let the first three digits contain an 8 or 9 (but don't be more restrictive than that), and make 
		// sure that the second set of three digits is not greater than 742. Hint: Think through the easiest way to construct the 
		// phone number. Each digit does not have to be determined separately.
		
		System.out.println("\nProblem 9:");
		DecimalFormat fmt2 = new DecimalFormat("000");
		DecimalFormat fmt3 = new DecimalFormat("0000");
		int digit1, digit2, digit3, clust2, clust3;
		
		digit1 = rand.nextInt(8);
		digit2 = rand.nextInt(8);
		digit3 = rand.nextInt(8);
		
		clust2 = rand.nextInt(742);
		clust3 = rand.nextInt(10000);
		
		System.out.println("Random phone number: " +digit1+digit2+digit3+ "-" +fmt2.format(clust2)+ "-" +fmt3.format(clust3));
		
		
		// PP 3.5
		// Write an application that reads the radius of a sphere and prints its volume and surface area. Use the following
		// formulas, in which r represents the sphere's radius. Print the output to four decimal places.
		
		System.out.println("\nProblem 10:");
		
		int radius;
		double volume, surfArea;
		
		double pi = Math.PI;
		DecimalFormat fmt4 = new DecimalFormat("0.####");
		
		System.out.print("Enter the radius of the sphere: ");
		radius = scan.nextInt();
		
		volume = (4.0*pi*Math.pow(radius, 3))/ 3;
		surfArea = 4.0*pi*Math.pow(radius, 2);
		
		System.out.println("Volume: " +fmt4.format(volume));
		System.out.println("Surface Area: " +fmt4.format(surfArea));
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
