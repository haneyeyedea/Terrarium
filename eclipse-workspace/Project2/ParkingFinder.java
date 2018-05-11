 /**
  * This program uses the ParkingSpot class to create four parking spots and compare them with the location of a 
  * randomly placed vehicle. The closest parking spot and its object information will be displayed. 
  *
  * @author Melodee Haney
  */

import java.util.Scanner;
import java.util.Random;
import java.text.NumberFormat;

public class ParkingFinder 
{

	public static void main(String[] args) 
	{
		
		// Collect random seed and Parking Time
		Scanner in = new Scanner(System.in);

		System.out.print("Enter random seed: ");
		long seed = in.nextLong();
		System.out.print("Enter parking time (minutes): ");
		int time = in.nextInt();
		
		if (time < 0)
		{
			System.out.println("You can't park here for negative time. Try somewhere else");
			return;
		}
		// Generate car coordinates
		Random generator = new Random(seed);
		
		int carX = generator.nextInt(100);
		int carY = generator.nextInt(100);
		
		System.out.print("\nPosition of vehicle: x = " +carX+ " y = " +carY);
		
		// Generate four random Parking Spots
		int locationX = generator.nextInt(100);
		int locationY = generator.nextInt(100);
		ParkingSpot spot1 = new ParkingSpot("1st Street", locationX, locationY);
		
		locationX = generator.nextInt(100);
		locationY = generator.nextInt(100);
		ParkingSpot spot2 = new ParkingSpot("2nd Street", locationX, locationY);
		
		locationX = generator.nextInt(100);
		locationY = generator.nextInt(100);
		ParkingSpot spot3 = new ParkingSpot("3rd Street", locationX, locationY);
		spot3.setCostPerInterval(0.30);
		
		locationX = generator.nextInt(100);
		locationY = generator.nextInt(100);
		ParkingSpot spot4 = new ParkingSpot("4th Street", locationX, locationY);
		spot4.setCostPerInterval(0.30);
		
		// Calculate the Distance from and Charge of each Parking Spot
		
		int distance1 = Math.abs(spot1.getLocationX()-carX) + Math.abs(spot1.getLocationY()-carY);
		int distance2 = Math.abs(spot2.getLocationX()-carX) + Math.abs(spot2.getLocationY()-carY);
		int distance3 = Math.abs(spot3.getLocationX()-carX) + Math.abs(spot3.getLocationY()-carY);
		int distance4 = Math.abs(spot4.getLocationX()-carX) + Math.abs(spot4.getLocationY()-carY);
		
		NumberFormat currencyFmt = NumberFormat.getCurrencyInstance();
		double cost1 = spot1.getCostPerInterval()*Math.ceil(time / 10.0);
		double cost2 = spot2.getCostPerInterval()*Math.ceil(time / 10.0);
		double cost3 = spot3.getCostPerInterval()*Math.ceil(time / 10.0);
		double cost4 = spot4.getCostPerInterval()*Math.ceil(time / 10.0);
		
		System.out.println("\n\nSpot 1: " +spot1+ "\n\tDistance: " +distance1+ "\n\tTotal cost: " +currencyFmt.format(cost1));
		System.out.println("\nSpot 2: " +spot2+ "\n\tDistance: " +distance2+ "\n\tTotal cost: " +currencyFmt.format(cost2));
		System.out.println("\nSpot 3: " +spot3+ "\n\tDistance: " +distance3+ "\n\tTotal cost: " +currencyFmt.format(cost3));
		System.out.println("\nSpot 4: " +spot4+ "\n\tDistance: " +distance4+ "\n\tTotal cost: " +currencyFmt.format(cost4));
		
		// Find the Parking Spot that is closest and print the Object 
		int min = 201;
		int i = 0;
		
		if (distance1<min)
		{
			i = 1;
			min = distance1;
		}
		if (distance2<min)
		{
			i = 2;
			min = distance2;
		}
		if (distance3<min)
		{
			i = 3;
			min = distance3;
		}
		if (distance4<min)
		{
			i = 4;
			min = distance4;
		}
		
		switch (i)
		{
		case 1:
			System.out.println("\nDistance to closest spot: " +distance1);
			System.out.println("Closest spot: " +spot1);
			break;
		case 2:
			System.out.println("\nDistance to closest spot: " +distance2);
			System.out.println("Closest spot: " +spot2);
			break;
		case 3:
			System.out.println("\nDistance to closest spot: " +distance3);
			System.out.println("Closest spot: " +spot3);
			break;
		case 4:
			System.out.println("\nDistance to closest spot: " +distance4);
			System.out.println("Closest spot: " +spot4);
			break;
		default:
			System.out.println("The closest Parking Spot was not successfully found.");
		}
		
		in.close();
	}

}
