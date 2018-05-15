import java.awt.Color;
import javax.swing.JButton;

public class LitePegButton extends JButton{
	
	private final Color VIOLET = new Color(127,0, 255);
	private final Color[] COLORS = {Color.BLACK, Color.RED, Color.BLUE, Color.ORANGE, Color.WHITE, Color.GREEN, Color.YELLOW,
			Color.PINK, VIOLET}; 	// array of possible colors
	private int colorIndex;			// current color index
	
	public LitePegButton() {
		
		this.setBackground(COLORS[colorIndex]);
		this.setOpaque(true); // MacOSX fix for background color.
		this.setBorderPainted(false); // MacOSX fix for background color.
		
	}
	
	public Color getColor() {
		
		return COLORS[colorIndex];    // need to change to correct color output
	}
	
	// changes index to 0 and updates background color of the button
	public void resetColor() {
		
		colorIndex = 0;
		this.setBackground(COLORS[colorIndex]);
	}
	
	// changes index to next color and updates the background color of the button
	public void changeColor() {
		
		colorIndex = (colorIndex + 1) % COLORS.length;
		this.setBackground(COLORS[colorIndex]);
	}

	
}
