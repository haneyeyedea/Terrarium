import java.io.FileNotFoundException;

public class GridMonitorDriver {
	
	public static void main (String[] args) throws FileNotFoundException {
		
		String filename = args[0];
		GridMonitor grid = new GridMonitor(filename);
		
		double[][] baseGrid = grid.getBaseGrid();
		
		System.out.println("Base Grid\n--------------------");
		for (int i=0; i < baseGrid.length; i++) {
			for (int j=0; j < baseGrid[0].length; j++) {
				System.out.print(baseGrid[i][j] + "\t");
			}
			System.out.println("");
		}
		
		double[][]surroundingSumGrid = grid.getSurroundingSumGrid();
		
		System.out.println("\nSurrounding Sum Grid\n--------------------");
		for (int i=0; i < baseGrid.length; i++) {
			for (int j=0; j < baseGrid[0].length; j++) {
				System.out.print(surroundingSumGrid[i][j] + "\t");
			}
			System.out.println("");
		}
		
		double[][]surroundingAvgGrid = grid.getSurroundingAvgGrid();
		
		System.out.println("\nSurrounding Average Grid\n--------------------");
		for (int i=0; i < baseGrid.length; i++) {
			for (int j=0; j < baseGrid[0].length; j++) {
				System.out.print(surroundingAvgGrid[i][j] + "\t");
			}
			System.out.println("");
		}
		
		double[][]deltaGrid = grid.getDeltaGrid();
		
		System.out.println("\nDelta Grid\n--------------------");
		for (int i=0; i < baseGrid.length; i++) {
			for (int j=0; j < baseGrid[0].length; j++) {
				System.out.print(deltaGrid[i][j] + "\t");
			}
			System.out.println("");
		}
		
boolean[][]dangerGrid = grid.getDangerGrid();
		
		System.out.println("\nDanger Grid\n--------------------");
		for (int i=0; i < baseGrid.length; i++) {
			for (int j=0; j < baseGrid[0].length; j++) {
				System.out.print(dangerGrid[i][j] + "\t");
			}
			System.out.println("");
		}
	}
}
