import java.util.Scanner;

/**
 * Requests grid size and optional seed, calls RandomWalk with given grid size and seed, then prints the coordinates
 * of each step.
 * @author melodee
 *
 */
public class RandomWalkDriver 
{
	public static void main (String[] args)
	{
		Scanner scan = new Scanner(System.in);

		int gridSize = 0;
		long seed = -1;
		int i = 0;
		
		while (gridSize <= 0)
		{
			if (i != 0 && gridSize <=0)
				System.out.println("Error: grid size must be positive!");
			System.out.print("Enter grid size: ");
			gridSize = scan.nextInt();
			i++;
		}
		
		i = 0;
		while (seed < 0)
		{
			if (i != 0 && seed < 0)
				System.out.println("Error: random seed must be >= 0!");
			System.out.print("Enter random seed (0 for no seed): ");
			seed = scan.nextInt();
			i++;
		}
		
		RandomWalk walk;
		if (seed == 0)
		{
			walk = new RandomWalk(gridSize);
		}
		else
		{
			walk = new RandomWalk(gridSize,seed);
		}
		
		walk.createWalk();
		System.out.println(walk);
		
		scan.close();
	}
}
