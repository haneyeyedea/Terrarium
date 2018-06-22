import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 * 
 * @author mvail melodeehaney
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	// constants you may find useful
	private int ROWS; // initialized in constructor
	private int COLS; // initialized in constructor
	private final char OPEN = 'O'; // capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/**
	 * Construct a CircuitBoard from a given board input file, where the first line
	 * contains the number of rows and columns as ints and each subsequent line is
	 * one row of characters representing the contents of that position. Valid
	 * characters are as follows: 'O' an open position 'X' an occupied, unavailable
	 * position '1' first of two components needing to be connected '2' second of
	 * two components needing to be connected 'T' is not expected in input files -
	 * represents part of the trace connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 *            file containing a grid of characters
	 * @throws FileNotFoundException
	 *             if Scanner cannot read the file
	 * @throws InvalidFileFormatException
	 *             for any other format or content issue that prevents reading a
	 *             valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		Scanner fileScan = new Scanner(new File(filename));

		int gridDim;
		int lineCount = 0;
		boolean validChar = false;
		boolean validStart = false;
		boolean validEnd = false;

		while (fileScan.hasNextLine()) {
			String line = fileScan.nextLine();

			if (lineCount == 0) {
				Scanner charScan = new Scanner(line);
				int numCount = 0;

				while (charScan.hasNext()) {
					try {
						gridDim = charScan.nextInt();
					}
					catch(InputMismatchException e) {
						charScan.close();
						throw new InvalidFileFormatException("The first line of the file must contain two integers: ROWS COLS");
					}
					if (numCount == 0) {
						ROWS = gridDim;
					} else {
						COLS = gridDim;

						if (charScan.hasNext()) {
							charScan.close();
							throw new InvalidFileFormatException(
									"The first line of the file must contain two integers: ROWS COLS");
						}
					}
					numCount++;
				}
				charScan.close();
			}

			else {
				if (lineCount == 1) {
					board = new char[ROWS][COLS];
				}
				if (lineCount > ROWS) {
					throw new InvalidFileFormatException("The number of rows should be " + ROWS);
				}
				if (line.length() != COLS * 2) {
					throw new InvalidFileFormatException("The number of columns in row " + (lineCount-1) + " should be "
							+ COLS + ". Str Length: " + line.length());
				}
				for (int i = 0; i < line.length(); i += 2) {
					if(line.charAt(i) == OPEN) {validChar = true;}
					else if (line.charAt(i) == CLOSED) {validChar = true;}
					else if (line.charAt(i) == START) {
						if(validStart == true) {throw new InvalidFileFormatException("There may only be one starting position");}
						startingPoint = new Point((lineCount-1), (i/2));
						validStart = true;
						validChar = true;
					}
					else if(line.charAt(i) == END) {
						if(validEnd == true) {throw new InvalidFileFormatException("There may only be one ending position");}
						endingPoint = new Point((lineCount-1), (i/2));
						validEnd = true;
						validChar = true;
					}
					
					if(validChar == false) {throw new InvalidFileFormatException("Invalid character at " + (lineCount-1) + ", " + (i/2));}
					
					board[lineCount - 1][i/2] = line.charAt(i);
					validChar = false;
				}
			}
			lineCount++;
		}
		if(!validStart || !validEnd) {throw new InvalidFileFormatException("There must be both a starting and ending point");}
		fileScan.close();
	}

	

	/**
	 * Copy constructor - duplicates original board
	 * 
	 * @param original
	 *            board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/**
	 * utility method for copy constructor
	 * 
	 * @return copy of board array
	 */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}

	/**
	 * Return the char at board position x,y
	 * 
	 * @param row
	 *            row coordinate
	 * @param col
	 *            col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Return whether given board position is open
	 * 
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}

	/**
	 * Set given position to be a 'T'
	 * 
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException
	 *             if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}

	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}

	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}

	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}

	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}

}// class CircuitBoard
