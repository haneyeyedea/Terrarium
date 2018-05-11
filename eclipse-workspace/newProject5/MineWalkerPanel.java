import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// The main game panel class. Must extend JPanel. Creates and adds sub-panels. Manages all game components and the ActionListeners
@SuppressWarnings("serial")
public class MineWalkerPanel extends JPanel{
	
	private final static int DEFAULT_FIELD_DIM = 10;
	private final static int DEFAULT_DIFF = 2;
	private final static int DELAY = 750;
	private int dim = DEFAULT_FIELD_DIM;
	private int diff = DEFAULT_DIFF;
	private int time = 0;
	private int moves;
	private int lives = 5;
	private boolean minesVisible;
	private boolean pathVisible;
	private boolean newGameDisable;
	private int score = 1000;
	private String[] settings = {"easy","normal","hard"};
	
	// Create colors
	private Color[] colors;
			
	// declare panels
	private MineFieldPanel board;
	private JPanel colorKey;
	private JPanel scoreBoard;
	private JPanel useButtons;	
	
	// declare usable buttons
	private JButton showMines = new JButton("Show Mines");
	private JButton showPath = new JButton("Show Path");
	private JButton newGame = new JButton("Give Up?");
	
	// declare color key buttons
	private JPanel zeroInd;
	private JPanel oneInd;
	private JPanel twoInd;
	private JPanel threeInd;
	private JPanel exploded;
	private JPanel blank;
	private JPanel startSquare;
	private JPanel endSquare;
	
	// declare text fields
	private JLabel scoreSpace = new JLabel("-------------------------");
	private JLabel livesCount;
	private JLabel movesCount;
	private JLabel scoreCount;
	private JLabel gridSize = new JLabel("Grid Size: " + dim);
	private JLabel diffSize = new JLabel("Difficulty: " + settings[diff-1]);
	
	// declare grid size slider
	private JLabel slideSpace = new JLabel("-------------------------");
	private JLabel gridLabel = new JLabel("Select Grid Size:");
	private JSlider gridSlider;
	
	// declare difficulty slider
	private JLabel diffSpace = new JLabel("-------------------------");
		private JLabel diffLabel = new JLabel("Select Difficulty:");
		private JSlider diffSlider;
		
	public MineWalkerPanel() {
		newWalkPanel();
		showMines.addActionListener(new MineListener());
		showPath.addActionListener(new PathListener());
		newGame.addActionListener(new GameListener());
		
		startAnimation();
	}
	
	private void newWalkPanel() {
		this.removeAll();
		
		moves = 0;
		lives = 5;
		score = 1000;
		
		minesVisible = false;
		pathVisible = false;
		newGameDisable = false;
		setLayout(new BorderLayout());
		board = new MineFieldPanel(new SquareListener(),dim,diff);
		colors = board.getColors();
		
		// create usable buttons panel
		useButtons = new JPanel();
		
		// create color key
		colorKey = new JPanel();
		colorKey.setBorder(BorderFactory.createTitledBorder("Color Key"));
		colorKey.setLayout(new BoxLayout(colorKey, BoxLayout.Y_AXIS));
		
		zeroInd = new JPanel();
		zeroInd.setBorder(BorderFactory.createRaisedBevelBorder());
		zeroInd.add(new JLabel("0 Adjacent Mines"));
		zeroInd.setBackground(colors[0]);
		
		oneInd = new JPanel();
		oneInd.setBorder(BorderFactory.createRaisedBevelBorder());
		oneInd.add(new JLabel("1 Adjacent Mine"));
		oneInd.setBackground(colors[1]);
		
		twoInd = new JPanel();
		twoInd.setBorder(BorderFactory.createRaisedBevelBorder());
		twoInd.add(new JLabel("2 Adjacent Mines"));
		twoInd.setBackground(colors[2]);
		
		threeInd = new JPanel();
		threeInd.setBorder(BorderFactory.createRaisedBevelBorder());
		threeInd.add(new JLabel("3 Adjacent Mines"));
		threeInd.setBackground(colors[3]);
		
		exploded = new JPanel();
		exploded.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel expTitle = new JLabel("Exploded Mines");
		expTitle.setForeground(Color.WHITE);
		exploded.add(expTitle);
		exploded.setBackground(colors[4]);
		
		blank = new JPanel();
		
		startSquare = new JPanel();
		startSquare.setBorder(BorderFactory.createRaisedBevelBorder());
		startSquare.add(new JLabel("Start"));
		startSquare.setBackground(colors[5]);
		
		endSquare = new JPanel();
		endSquare.setBorder(BorderFactory.createRaisedBevelBorder());
		endSquare.add(new JLabel("End"));
		endSquare.setBackground(colors[6]);
		
		// create scoreboard
		scoreBoard = new JPanel();
		TitledBorder tb = BorderFactory.createTitledBorder("Scoreboard");
		tb.setTitleJustification(TitledBorder.RIGHT);
		scoreBoard.setBorder(tb);
		scoreBoard.setLayout(new BoxLayout(scoreBoard, BoxLayout.Y_AXIS));
		
		livesCount = new JLabel("Lives: " +lives);
		movesCount = new JLabel("Moves: " +moves);
		scoreCount = new JLabel("Score: " +score);
		
		gridSlider = new JSlider(10,30,dim);
		gridSlider.setOrientation(SwingConstants.VERTICAL);
		gridSlider.setPaintTicks(true);
		gridSlider.setPaintLabels(true);
		gridSlider.setMajorTickSpacing(5);
		gridSlider.setSnapToTicks(true);
		gridSlider.addChangeListener(new SlideListener());
		
		diffSlider = new JSlider(1,3,diff);
		diffSlider.setOrientation(SwingConstants.VERTICAL);
		diffSlider.setPaintTicks(true);
		diffSlider.setPaintLabels(true);
		diffSlider.setMajorTickSpacing(1);
		diffSlider.setSnapToTicks(true);
		diffSlider.addChangeListener(new DiffListener());
		
		addWalkPanels();
		
		this.invalidate();
		this.revalidate();
	}
	
	private void addWalkPanels() {
		useButtons.add(showMines);
		useButtons.add(showPath);
		useButtons.add(newGame);
		colorKey.add(zeroInd);
		colorKey.add(oneInd);
		colorKey.add(twoInd);
		colorKey.add(threeInd);
		colorKey.add(exploded);
		colorKey.add(blank);
		colorKey.add(startSquare);
		colorKey.add(endSquare);
		scoreBoard.add(scoreSpace);
		scoreBoard.add(livesCount);
		scoreBoard.add(movesCount);
		scoreBoard.add(scoreCount);
		scoreBoard.add(slideSpace);
		scoreBoard.add(gridLabel);
		scoreBoard.add(gridSlider);
		scoreBoard.add(diffSpace);
		scoreBoard.add(diffLabel);
		scoreBoard.add(diffSlider);
		add(board, BorderLayout.CENTER);
		add(scoreBoard, BorderLayout.EAST);
		add(colorKey, BorderLayout.WEST);
		add(useButtons,BorderLayout.SOUTH);	
	}
	private void refreshScoreBoard() {
		scoreBoard.removeAll();
		
		livesCount = new JLabel("Lives: " +lives);
		movesCount = new JLabel("Moves: " +moves);
		scoreCount = new JLabel("Score: " +score);
		gridSize = new JLabel("Grid Size: " + dim);
		diffSize = new JLabel("Difficulty: " + settings[diff-1]);
		
		scoreBoard.add(scoreSpace);
		scoreBoard.add(livesCount);
		scoreBoard.add(movesCount);
		scoreBoard.add(scoreCount);
		scoreBoard.add(slideSpace); 
		if (moves == 0) {
			scoreBoard.add(gridLabel); 
			scoreBoard.add(gridSlider);
			scoreBoard.add(diffSpace);
			scoreBoard.add(diffLabel);
			scoreBoard.add(diffSlider);
			}
		else {scoreBoard.add(gridSize); scoreBoard.add(diffSize);}
		
		scoreBoard.invalidate();
		scoreBoard.revalidate();
	}
	
	private void calculateScore() {
		score = (score - 1);
		refreshScoreBoard();
	}
	
	private void endGamePop(boolean winLose) {
		String ifWinner;
		if(winLose) {ifWinner = "win!!";}
		else {ifWinner = "lose..";}
		int input = JOptionPane.showConfirmDialog(null,
				"Final Score: " +score+ "\nTotal Moves: " +moves+ "\nDo you want to play again?", "You " +ifWinner, JOptionPane.YES_NO_OPTION);
		if(input == JOptionPane.NO_OPTION)
		{
		    System.exit(0);
		}
		newGameDisable = true;
		newGame.setText("New Game");
	}
	
	private class MineListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(minesVisible) {
				showMines.setText("Show Mines");
				minesVisible = false;
			}
			else {
				minesVisible = true;
				showMines.setText("Hide Mines");
			}
			
			board.setMineColor(minesVisible);
			board.createPanel();
		}
		
	}
	
	private class PathListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(pathVisible) {
				showPath.setText("Show Path");
				pathVisible = false;
			}
			else {
				showPath.setText("Hide Path");
				pathVisible = true;
			}
			
			board.setPathColor(pathVisible);
			board.createPanel();
		}
		
	}
	
	private class GameListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(newGameDisable) {
				newGame.setText("Give Up?");
				showPath.setText("Show Path");
				showMines.setText("Show Mines");
				
				board.setPathColor(pathVisible);
				board.setMineColor(minesVisible);
				
				dim = DEFAULT_FIELD_DIM;
				diff = DEFAULT_DIFF;
				
				newWalkPanel();
			}
			
			else {
				newGame.setText("New Game");
				newGameDisable = true;
				showPath.setText("Hide Path");
				pathVisible = true;
				showMines.setText("Hide Mines");
				minesVisible = true;
				
				board.setPathColor(pathVisible);
				board.setMineColor(minesVisible);
				
				board.createPanel();
			}
			
		}
		
	}
	
	private class SlideListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
				dim = (int)source.getValue();
				newWalkPanel();
			}
			
		}
		
	}
	
	private class DiffListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider diffSource = (JSlider)e.getSource();
			if (!diffSource.getValueIsAdjusting()) {
				diff = (int)diffSource.getValue();
				newWalkPanel();
			}
			
		}
		
	}
	
	private class SquareListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			MineFieldButton clicked = (MineFieldButton) e.getSource();
			
			scoreBoard.remove(gridLabel);
			scoreBoard.remove(gridSlider);
			scoreBoard.remove(diffSpace);
			scoreBoard.remove(diffLabel);
			scoreBoard.remove(diffSlider);
			int x = clicked.getCoorX();
			int y = clicked.getCoorY();
			
			if (clicked.getMineBool()) {
				clicked.setExploded();
				lives--;
			}
			else {
				board.setCurrentX(x);
				board.setCurrentY(y);
			}
			clicked.setBeenThere();
			board.createPanel();
			moves++;
			refreshScoreBoard();
			
			if(x == 0 && y == dim - 1) {
				board.deActiveButtons();
				minesVisible = true;
				showMines.setText("Hide Mines");
				endGamePop(true);
			}
			if(lives<=0 || score <= 0) {
				board.deActiveButtons();
				minesVisible = true;
				showMines.setText("Hide Mines");
				endGamePop(false);
			}
		}
	}
	
	/**
     * Performs action when timer event fires.
     */
	private class TimerActionListener implements ActionListener{
		public void actionPerformed(ActionEvent evt){
            time++;
            board.blinkCurrent(time%2);
            calculateScore();
		}
	}

   /**
    * Create an animation thread that runs periodically
    */
    private void startAnimation(){
	    TimerActionListener taskPerformer = new TimerActionListener();
	    new Timer(DELAY, taskPerformer).start();
    	}
}
