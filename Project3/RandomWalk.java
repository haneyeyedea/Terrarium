import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Creates a random walk from [0,n] to [n,0] where the walk proceeds with steps randomly in the North and East
 * directions with equal probability
 * 
 * @author melodee
 *
 */
public class RandomWalk implements RandomWalkInterface
{
	private int gridSize;
	private boolean done;
	
	private ArrayList<Point> path = new ArrayList<Point>();
	
	private Point start;
	private Point end;
	private Point current;
	
	Random rand;
	
	/**
	 * Using grid size, constructs an array list of points with the start point of [0,n]
	 * @param gridSize
	 */
	public RandomWalk(int gridSize)
	{
		this.gridSize = gridSize;
		done = false;
		
		start = new Point (0,gridSize-1);
		end = new Point(gridSize-1,0);
		current = new Point (0,gridSize-1);
		
		Point p1 = new Point(start.x, start.y);
        path.add(p1);
		
        rand = new Random();
	}
	/**
	 * Using grid size and a random seed, constructs an array list of points with the start point of [0,n] and inits
	 * a random with the given seed
	 * @param gridSize
	 * @param seed
	 */
	public RandomWalk(int gridSize, long seed)
	{
		this.gridSize = gridSize;
		
		done = false;
		
		start = new Point (0,gridSize-1);
		end = new Point(gridSize-1,0);
		current = start;
		
		Point p1 = new Point(start.x, start.y);
        path.add(p1);
		
        rand = new Random(seed);
	}
	/* 
	 * (non-Javadoc)
	 * @see RandomWalkInterface#step()
	 */
	public void step()
	{
		boolean trig = true;
		while (trig)
		{
			int stepDir = rand.nextInt(2);
			if (stepDir == 0)
			{
				if (current.x < end.x)
				{
					current.x += 1;
					trig = false;
				}
			}
			else
			{
				if (current.y > end.y)
				{
					current.y -= 1;
					trig = false;
				}
			}
		}
		
		Point p1 = new Point(current.x, current.y);
        path.add(p1);
        
		if (current.x == end.x && current.y == end.y)
		{
			done = true;
		}
			
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#stepEC()
	 */
	public void stepEC()
	{
		boolean trig = true;
		while (trig)
		{
			int stepBackFor = rand.nextInt(10);
			int stepDir = rand.nextInt(2);
			if (stepBackFor <= 8)
			{
				if (stepDir == 0)
				{
					if (current.x < end.x)
					{
						current.x += 1;
						trig = false;
					}
				}
				else
				{
					if (current.y > end.y)
					{
						current.y -= 1;
						trig = false;
					}
				}
			}
			else
			{
				if (stepDir == 0)
				{
					if (current.x > start.x)
					{
						current.x -= 1;
						trig = false;
					}
				}
				else
				{
					if (current.y > start.y)
					{
						current.y += 1;
						trig = false;
					}
				}
			}
		}
		
		Point p1 = new Point(current.x, current.y);
        path.add(p1);
        
		if (current.x == end.x && current.y == end.y)
		{
			done = true;
		}
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#createWalk()
	 */
	public void createWalk()
	{

		while (!done)
		{
			step();
		}
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#createWalkEC()
	 */
	public void createWalkEC()
	{
		while (!done)
		{
			stepEC();
		}
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#isDone()
	 */
	public boolean isDone()
	{
		return done;
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#getGridSize()
	 */
	public int getGridSize()
	{
		return gridSize;
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#getStartPoint()
	 */
	public Point getStartPoint()
	{
		return start;
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#getEndPoint()
	 */
	public Point getEndPoint()
	{
		return end;
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#getCurrentPoint()
	 */
	public Point getCurrentPoint()
	{
		return current;
	}
	/* (non-Javadoc)
	 * @see RandomWalkInterface#getPath()
	 */
	public ArrayList<Point> getPath()
	{
		return path;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String str = "";
		for (Point p : path)
		{
			str += "[" +p.x+ "," +p.y+ "] ";
		}
		return str;
	}
}