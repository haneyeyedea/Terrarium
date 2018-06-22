import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class GUIPanel extends JPanel{

	private int rows;
	private int cols;
	
	// Create colors
	private final Color red = Color.red;
			
	// declare panels
	private JPanel fileMenu;
	private JPanel board;
	private JPanel selector;	
	
	// declare color key buttons
	private JPanel zeroInd;
	private JPanel oneInd;
	private JPanel twoInd;
	private JPanel threeInd;
	private JPanel exploded;
	private JPanel startSquare;
	private JPanel endSquare;
	
	// declare slider
	private JSlider slider;
	public GUIPanel(CircuitBoard board, ArrayList<TraceState> bestPaths) {
		setLayout(new BorderLayout());
	}
}
