import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Animated program with a ball bouncing off the program boundaries
 * @author mvail
 * @author Melodee Haney
 */
@SuppressWarnings("serial")
public class BouncyBall extends JPanel
{
	private final int INIT_WIDTH = 600;
	private final int INIT_HEIGHT = 400;
	private final int DELAY = 60;       // milliseconds between Timer events

	private final int DELTA_RANGE = 20; // range for xDelta and yDelta
	private final int MAX_RADIUS = 20;  // maximum radius value
	private final int MIN_RADIUS = 5;   // minimum radius value

	private Random rand;                // random number generator
	private Color color;                // initial ball color

	private int x, y;                   // ball anchor point coordinates
	private int xDelta, yDelta;         // change in x and y from one step to the next

	private int radius = 10;            // ball radius
	private int rDelta = 1;             // change in radius from one step to the next
	
	private boolean directionX = true;
	private boolean directionY = true;
	
	private boolean directionR = true;
	
	private boolean dirColorR = true;
	private boolean dirColorG = true;
	private boolean dirColorB = true;
	
	private int colDelta = 1;

	/**
	 * Draws a filled bouncy ball that stays within the bounds of the screen.
	 *
	 * @param g Graphics context
	 */
	public void paintComponent(Graphics g)
	{
		int width = getWidth();
		int height = getHeight();

		// Calculate new y anchor point value
		if(y <= radius)
		{
			directionY = true;
			y = radius;
		}
		
		if(y >= height - radius)
		{
			directionY = false;
			y = height - radius;
		}
		
		if(directionY == true)
		{
			if(yDelta > 0)
				y += yDelta;
			else
				y -= yDelta;
				
		}
		else
		{
			if(yDelta > 0)
				y -= yDelta;
			else
				y += yDelta;
		}
			
		// Calculate new x anchor point value
		if(x <= 0)
		{
			directionX = true;
			x = radius;
		}
		if(x >= width - radius)
		{
			directionX = false;
			x = width - radius;
		}
		
		if(directionX == true)
		{
			if(xDelta > 0)
				x += xDelta;
			else
				x -= xDelta;
		}
		else
		{
			if (xDelta > 0)
				x -= xDelta;
			else
				x += xDelta;
		}
			

		// Calculate new radius
		if(radius <= MIN_RADIUS)
			directionR = true;
		if(radius >= MAX_RADIUS)
			directionR = false;
		
		if (directionR == true)
			radius += rDelta;
		else
			radius -= rDelta;
		
		// Calculate new color
		int colorR = color.getRed();
		int colorG = color.getGreen();
		int colorB = color.getBlue();
		
		if(colorR <= 1)
			dirColorR = true;
		if(colorR >= 254)
			dirColorR = false;
		
		if(dirColorR == true)
			colorR += colDelta;
		else
			colorR -= colDelta;
		  
		
		
		if(colorG <= 1)
			dirColorG = true;
		if(colorG >= 254)
			dirColorG = false;
		
		if(dirColorG == true)
			colorG += colDelta;
		else
			colorG -= colDelta;
		
		
		
		if(colorB <= 1)
			dirColorB = true;
		if(colorB >= 254)
			dirColorB = false;
		
		if(dirColorB == true)
			colorB += colDelta;
		else
			colorB -= colDelta;
		
		color = new Color(colorR, colorG, colorB);
			

		// Paint the ball at the calculated anchor point
		g.setColor(color);
		g.fillOval(x-radius, y-radius, 2*radius, 2*radius);

		// Makes the animation smoother
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Constructor for the display panel initializes necessary variables.
	 * Only called once, when the program first begins.
	 * This method also sets up a Timer that will call paintComponent() 
	 * with frequency specified by the DELAY constant.
	 */
	public BouncyBall()
	{
		setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
		this.setDoubleBuffered(true);
		setBackground(Color.black);

		 // Instantiate instance variable for reuse in paintComponent()
		rand = new Random();
		
		// Initialize ball color
		int colorR = rand.nextInt(256);
		int colorG = rand.nextInt(256);
		int colorB = rand.nextInt(256);
		
		color = new Color(colorR, colorG, colorB);

		// Initialize ball anchor point within panel bounds
		x = rand.nextInt(INIT_WIDTH - radius) + 2*radius;
		y = rand.nextInt(INIT_HEIGHT - radius) + 2*radius;


		// Initialize deltas for x and y
		xDelta = rand.nextInt(DELTA_RANGE) - DELTA_RANGE/2;
		yDelta = rand.nextInt(DELTA_RANGE) - DELTA_RANGE/2;
		// TODO: replace with random deltas from -DELTA_RANGE/2
		// to +DELTA_RANGE/2

		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/**
	 * Create an animation thread that runs periodically.
	 * DO NOT MODIFY.
	 */
	private void startAnimation() {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		};
		new Timer(DELAY, taskPerformer).start();
	}

	/**
	 * Starting point for the BouncyBall program.
	 * DO NOT MODIFY.
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Bouncy Ball");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BouncyBall());
		frame.pack();
		frame.setVisible(true);
	}
}
