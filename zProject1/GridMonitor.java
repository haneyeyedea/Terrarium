import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads a file of a grid, copies the grid into an array of doubles, and determines if each cell is at risk of exceeding 50% of the value
 * of the adjacent blocks. Creates a boolean array of the same size, where a 'true' cell is at risk of exceeding. If cell is on the border
 * or corner, the cell uses itself as a value for each missing value. Getter methods defined for
 * 		1) the given 'base grid',
 * 		2) the calculated 'surrounding sum grid', sum of all adjacent cells,
 * 		3) the calculated 'surrounding average grid', average of all adjacent cells,
 * 		4) the calculated 'delta grid', the acceptable delta from average value for each cell, and
 * 		5) the observed 'danger grid', which returns true if the cell is at risk of exceeding the delta
 * 
 * @author melodee
 *
 */
public class GridMonitor implements GridMonitorInterface {
	
	private double[][] baseGrid;
	private double[][] surroundingSumGrid;
	private double[][] surroundingAvgGrid;
	private double[][] deltaGrid;
	private boolean[][] dangerGrid;
	
	private int firstDim;
	private int secondDim;
	
	/**
	 * Reads a file of a grid, copies the grid into an array of doubles, and determines if each cell is at risk of exceeding 50% of the 
	 * value of the adjacent blocks. Creates a boolean array of the same size, where a 'true' cell is at risk of exceeding. If cell is 
	 * on the border or corner, the cell uses itself as a value for each missing value.
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public GridMonitor(String filename) throws FileNotFoundException {
		//FileNotFoundException problem = new FileNotFoundException("File not found.");
		File file = new File(filename);
		
		if(file.exists() && file.isFile()) {
			//try {
				Scanner filescan = new Scanner(file);
				int gridDim;
				double number;
				int lineCount = 0;
			
				while(filescan.hasNextLine()) {
					String line = filescan.nextLine();
					Scanner numscan = new Scanner(line);
					int numCount = 0;
				
					while(numscan.hasNext()) {
						if (lineCount == 0) {
							gridDim = numscan.nextInt();
							if (numCount == 0) {
								firstDim = gridDim;
							}
							else {
								secondDim = gridDim;
								baseGrid = new double[firstDim][secondDim];
							}
						}
						else {							
							number = numscan.nextDouble();
							baseGrid[lineCount - 1][numCount] = number;
						}
						
						numCount++;
					}
				
					lineCount++;
					numscan.close();
				}
				filescan.close();
			//}
			//catch(FileNotFoundException e) {
				//System.out.println("File not found.");
			//}
		}
		//else {throw problem;}
		
		
		calculateSurroundingSumGrid();
		calculateSurroundingAvgGrid();
		calculateDeltaGrid();
		calculateDangerGrid();
	}
	
	/**
	 * Creates an array of the same dimensions as the base grid. Calculates the sum of all adjacent cells and places the value in the 
	 * specified cell. If the cell is on a border (or in a corner), the value of the specified cell is used in place of the missing cell
	 * 
	 */
	private void calculateSurroundingSumGrid() {
		surroundingSumGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				double counter = 0;
				
				if (i > 0) {counter += baseGrid[i-1][j];}
				else {counter += baseGrid[i][j];}
				
				if (i < firstDim - 1) {counter += baseGrid[i+1][j];}
				else {counter += baseGrid[i][j];}
				
				if (j > 0) {counter += baseGrid[i][j-1];}
				else {counter += baseGrid[i][j];}
				
				if (j < secondDim - 1) {counter += baseGrid[i][j+1];}
				else {counter += baseGrid[i][j];}
				
				surroundingSumGrid[i][j] = counter;
			}
		}
	}
	
	/**
	 * Creates an array of the same dimensions as the base grid. Calculates the average value of all adjacent cells and places the value in
	 * the specified cell. If the cell is on a border (or in a corner), the value of the specified cell is used in place of the missing cell
	 * 
	 */
	private void calculateSurroundingAvgGrid() {
		surroundingAvgGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				surroundingAvgGrid[i][j] = surroundingSumGrid[i][j] / 4;
			}
		}
	}
	
	/**
	 * Creates an array of the same dimensions as the base grid. Calculates the maximum delta from the average of 50% of the average and 
	 * places the value in the specified cell.
	 * 
	 */
	private void calculateDeltaGrid() {
		deltaGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				deltaGrid[i][j] = Math.abs(surroundingAvgGrid[i][j] / 2);
			}
		}
	}
	
	/**
	 * Creates an array of the same dimensions as the base grid. Cell will display as 'true' if the cell is at risk (if it is 50% different
	 * than adjacent cells
	 * 
	 */
	private void calculateDangerGrid() {
		dangerGrid = new boolean[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				double min = surroundingAvgGrid[i][j] - deltaGrid[i][j];
				double max = surroundingAvgGrid[i][j] + deltaGrid[i][j];
				boolean countBool = false;
				
				if (baseGrid[i][j] < min || baseGrid[i][j] > max) {countBool = true;}
				
				dangerGrid[i][j] = countBool;
			}
		}
	}
	@Override
	public double[][] getBaseGrid() {
		double[][] copyBaseGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				copyBaseGrid[i][j] = baseGrid[i][j];
			}
		}
		return copyBaseGrid;
	}
	@Override
	public double[][] getSurroundingSumGrid() {
		double[][] copySurroundingSumGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				copySurroundingSumGrid[i][j] = surroundingSumGrid[i][j];
			}
		}
		return copySurroundingSumGrid;
	}
	@Override
	public double[][] getSurroundingAvgGrid() {
		double[][] copySurroundingAvgGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				copySurroundingAvgGrid[i][j] = surroundingAvgGrid[i][j];
			}
		}
		return copySurroundingAvgGrid;
	}
	@Override
	public double[][] getDeltaGrid() {
		double[][] copyDeltaGrid = new double[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				copyDeltaGrid[i][j] = deltaGrid[i][j];
			}
		}
		return copyDeltaGrid;
	}
	@Override
	public boolean[][] getDangerGrid() {
		boolean[][] copyDangerGrid = new boolean[firstDim][secondDim];
		for(int i = 0; i < firstDim; i++) {
			for(int j = 0; j < secondDim; j++) {
				copyDangerGrid[i][j] = dangerGrid[i][j];
			}
		}
		return copyDangerGrid;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = "";
		for (int i=0; i < firstDim; i++) {
			for (int j=0; j < secondDim; j++) {
				str += dangerGrid[i][j] + "\t";
			}
			str += "\n";
		}
		return str;
	}
}
