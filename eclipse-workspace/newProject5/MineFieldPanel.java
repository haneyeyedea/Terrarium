import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

// The mine field panel class. Must extend JPanel. Creates and manages grid of MineFieldButtons.
@SuppressWarnings("serial")
public class MineFieldPanel extends JPanel{
	
	// Create colors
	private final Color ZEROCOLOR = Color.GREEN;
	private final Color ONECOLOR = Color.YELLOW;
	private final Color TWOCOLOR = Color.ORANGE;
	private final Color THREECOLOR = Color.RED;
	private final Color EXPLODEDCOLOR = Color.BLACK;
	private final Color STARTCOLOR = new Color(26, 102, 255);
	private final Color ENDCOLOR = new Color(153, 187, 255);
	private final Color MINECOLOR = new Color(255,111,101);
	private final Color SOLUTIONPATHCOLOR = new Color(105,255,81);
	private final Color PATHCOLOR = STARTCOLOR;
	private final Color[] colors = {ZEROCOLOR,ONECOLOR,TWOCOLOR,THREECOLOR,EXPLODEDCOLOR,STARTCOLOR,ENDCOLOR,MINECOLOR,SOLUTIONPATHCOLOR,PATHCOLOR};
		
	private static final int PERCENT_MINES_DEFAULT = 25;
	private MineFieldButton[][] buttons;
	private RandomWalk walk;
	ArrayList<Point> path;
	private int currentX;
	private int currentY;
	private int percentMines = PERCENT_MINES_DEFAULT;
	private int numMines;
	private int dim;
	private int diff;
	ActionListener listener;
	
	public MineFieldPanel(ActionListener listener, int dim, int diff) {
		this.listener = listener;
		this.dim = dim;
		this.diff = diff;
		buttons = new MineFieldButton[dim][dim];
		
		this.setLayout(new GridLayout(buttons.length, buttons[0].length, 1, 1));
		this.setPreferredSize(new Dimension(600, 600));
		
		newPanel();
	}
	
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	
	public void blinkCurrent(int blinkStatus) {
		buttons[currentX][currentY].blinkColor(blinkStatus);
	}
	
	/**
	 * Gets the color array for buttons and returns the array
	 * @return button color array set
	 */
	public Color[] getColors() {
		return colors;
	}
	
	/**
	 * Starts a new game with a new 'path', a new set of mines, and calls create panel
	 */
	public void newPanel() {
		createButtons();
		createRandomWalk();
		createMines();
		createPanel();
	}
	
	/**
	 * Creates/ recreates the MineFieldPanel, including removing the previous board, adding/ re-adding all buttons, setting the visible color 
	 * status of the buttons, and enables/ disables listeners for next-move buttons. Then revalidates the board
	 */
	public void createPanel() {
		this.removeAll();
		
		addButtons();
		setSquareNums();
		setColors();
		setActiveButtons();
		
		this.invalidate();
		this.revalidate();
	}
	
	public void debugging() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				System.out.print(buttons[i][j].getMineBool() + "\t");
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				System.out.print(buttons[i][j].getMineStatusNum() + "\t");
			}
			System.out.println();
		}
	}
	/**
	 * Takes the wanted dimension of the board and creates a dimension x dimension grid of MineFieldButtons
	 */
	public void createButtons() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new MineFieldButton();
				buttons[i][j].addActionListener(listener);
				buttons[i][j].setCoorX(i);
				buttons[i][j].setCoorY(j);
			}
		}
	}
	
	/**
	 * Adds all created MineFieldButtons to the MineFieldPanel
	 */
	public void addButtons() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				this.add(buttons[i][j]);
			}
		}
	}
	
	/**
	 * Creates a path from the start to the end using random walk and sets the buttons on the path as 'path' buttons. This is one solution to
	 * the mine field
	 */
	public void createRandomWalk() {
		// Call RandomWalk and create array list of points on the path
		walk = new RandomWalk(dim);
		walk.createWalk();
		path = walk.getPath();
		
		// Get the starting point and set initial current point
		Point start = walk.getStartPoint();
		currentX = (int) start.getY();
		currentY = (int) start.getX();
		
		// Set boolean value for solvable path
		for (Point p : path) {
			int xVal = (int) p.getY();
			int yVal = (int) p.getX();
			buttons[yVal][xVal].setPathBool(true);
		}
		
	}
	
	/**
	 * Takes percentage of mines and creates that number of mine buttons on the board
	 */
	public void createMines() {
		int minesCount = 0;
		Random rand = new Random();
		
		switch(diff) {
		case 1:
			percentMines = 15;
			break;
		case 2:
			percentMines = PERCENT_MINES_DEFAULT;
			break;
		case 3:
			percentMines = 35;
			break;
		}
		numMines = dim*dim*percentMines / 100;
		while (minesCount < numMines) {
			int xVal = rand.nextInt(buttons.length);
			int yVal = rand.nextInt(buttons[0].length);
			boolean curMineStat = buttons[yVal][xVal].getMineBool();
			boolean curPathStat = buttons[yVal][xVal].getPathBool();
			if(!curMineStat && ! curPathStat) {
				buttons[yVal][xVal].setMineBool(true);
			}
			minesCount = countMines();
		}
		
		
	}
	
	/**
	 * Counts how many total mines are on the board and returns that number
	 * @return total mines on the board
	 */
	public int countMines() {
		int numMines = 0;
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(buttons[i][j].getMineBool()) {
					numMines++;
				}
			}
		}
		return numMines;
	}
	
	public void deActiveButtons() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
		setMineColor(true);
	}
	/**
	 * Activates the next-move buttons to their action listener and allows click - mine disabled after exploded
	 */
	public void setActiveButtons() {
		
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				if(i >= 0 && j == currentY && i == currentX - 1) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setStatusShow();
				}
				else if(j >= 0 && j == currentY - 1 && i == currentX) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setStatusShow();
				}
				else if(i < buttons.length && j == currentY && i == currentX + 1) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setStatusShow();
				}
				else if(j < buttons[0].length && j == currentY + 1 && i == currentX) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setStatusShow();
				}
				else {
					buttons[i][j].setEnabled(false);
				}
				if(buttons[i][j].getExploded() && buttons[i][j].getBeenThere()) {buttons[i][j].setEnabled(false);}
			}
		}
		
	}
	
	/**
	 * If the button is on the solvable path and 'show path' is pressed, shows the solvable path on the MineFieldPanel
	 * 
	 * @param pathVisible
	 */
	public void setPathColor(Boolean pathVisible) {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(!buttons[i][j].getStatusShow() && buttons[i][j].getPathBool()) {
					buttons[i][j].setPathColor(colors[8]);
					buttons[i][j].setPathShow(pathVisible);
				}
			}
		}
	}
	
	/**
	 * If the button is a hidden mine and 'show mines' is pressed, shows the hidden mines on the MineFieldPanel
	 * 
	 * @param minesVisible
	 */
	public void setMineColor(Boolean minesVisible) {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(!buttons[i][j].getStatusShow() && buttons[i][j].getMineBool()) {
					buttons[i][j].setMineColor(colors[7]);
					buttons[i][j].setMineShow(minesVisible);
				}
			}
		}
	}
	
	/**
	 * Counts the surrounding mines and sets that number to each button
	 */
	public void setSquareNums() {
		
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				int mineStatusNum = 0;
				if (i > 0 && buttons[i-1][j].getMineBool()) {mineStatusNum++;}
				if (i < buttons.length - 1 && buttons[i+1][j].getMineBool()) {mineStatusNum++;}
				if (j > 0 && buttons[i][j-1].getMineBool()) {mineStatusNum++;}
				if (j < buttons[0].length - 1 && buttons[i][j+1].getMineBool()) {mineStatusNum++;}
				buttons[i][j].setMineStatusNum(mineStatusNum);
			}
		}
	}
	
	public void setColors() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(i == buttons.length - 1 && j == 0) {
					buttons[i][j].setStatusColor(colors[5]);
					buttons[i][j].setStatusShow();
					}
				else if(i == 0 && j == buttons[0].length - 1) {
					buttons[i][j].setStatusColor(colors[6]);
					buttons[i][j].setStatusShow();
					}
				else {buttons[i][j].setStatusColor(colors[buttons[i][j].getMineStatusNum()]);}
				if(!(buttons[i][j].getCoorX()==currentX && buttons[i][j].getCoorY()==currentY) && buttons[i][j].getBeenThere()) {
					buttons[i][j].setStatusColor(colors[9]);
					}
				if(buttons[i][j].getExploded()) {buttons[i][j].setStatusColor(colors[4]);}
				
			}
		}
	}
	
	public void resetStatus() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {buttons[i][j].resetStatus();}
		}
	}
}
