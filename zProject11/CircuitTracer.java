import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail melodeehaney
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;
	private GUIPanel displayBoard;
	private int bestLength;
	private boolean output = false;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		System.out.println("Three arguments required: ");
		System.out.println("     * first arg: -s for stack or -q for queue\r\n" + 
				"     * second arg: -c for console output or -g for GUI output\r\n" + 
				"     * third arg: input file name ");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws FileNotFoundException 
	 */
	private CircuitTracer(String[] args) throws FileNotFoundException {
		if(args[0].equals("-s")) {
			stateStore = Storage.getStackInstance();
		}
		else if(args[0].equals("-q")) {
			stateStore = Storage.getQueueInstance();
		}
		else {
			printUsage();
			System.out.println("Issue is with the First argument");
			System.exit(1);
		}
		if(args[1].equals("-c")) {
			output = false;
		}
		else if(args[1].equals("-g")) {
			output = true;
			System.out.println("GUI mode not yet supported");
			System.exit(1);
		}
		else {
			printUsage();
			System.out.println("Issue is with the Second argument");
			System.exit(1);
		}
		
		board = new CircuitBoard(args[2]);
		bestPaths = new ArrayList<TraceState>();
		bestLength = board.numRows() * board.numCols();
		
		int startX = (int) (board.getStartingPoint().getX());
		int startY = (int) (board.getStartingPoint().getY());
		if(board.isOpen((startX -1), startY)){
			stateStore.store(new TraceState(board, startX -1, startY));
		}
		if(board.isOpen((startX +1), startY)){
			stateStore.store(new TraceState(board, startX +1, startY));
		}
		if(board.isOpen((startX), (startY -1))){
			stateStore.store(new TraceState(board, startX, startY -1));
		}
		if(board.isOpen((startX), (startY +1))){
			stateStore.store(new TraceState(board, startX, startY +1));
		}
		
		while (!stateStore.isEmpty()) {
			TraceState tempPath = stateStore.retrieve();
			
			if (tempPath.isComplete()) {
				if(tempPath.pathLength() <= bestLength) {
					if(tempPath.pathLength() < bestLength && !bestPaths.isEmpty()) {
						bestPaths.clear();
					}
					bestPaths.add(tempPath);
					bestLength = tempPath.pathLength();
				}
			}
			else {
				int row = tempPath.getRow();
				int col = tempPath.getCol();
				if(tempPath.isOpen((row -1), col)){
					stateStore.store(new TraceState(tempPath, row -1, col));
				}
				if(tempPath.isOpen((row +1), col)){
					stateStore.store(new TraceState(tempPath, row +1, col));
				}
				if(tempPath.isOpen((row), (col -1))){
					stateStore.store(new TraceState(tempPath, row, col -1));
				}
				if(tempPath.isOpen((row), (col +1))){
					stateStore.store(new TraceState(tempPath, row, col +1));
				}
			}
		}
		
		if(output == false) {
			for(TraceState state: bestPaths) {
				System.out.println(state.toString());
			}
		}
		else {
			displayBoard = new GUIPanel(board, bestPaths);
		}
		//TODO: output results to console or GUI, according to specified choice

		
	}
} // class CircuitTracer
