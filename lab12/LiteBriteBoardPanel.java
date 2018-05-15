import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class LiteBriteBoardPanel extends JPanel{
	
	private LitePegButton[][] pegs;
	
	public LiteBriteBoardPanel(ActionListener listener, int width, int height) {
		
		pegs = new LitePegButton[height][width];
		
		this.setLayout(new GridLayout(pegs.length, pegs[0].length, 1, 1));
		this.setPreferredSize(new Dimension(800, 800));
		
		for(int i = 0; i < pegs.length; i++) {
			for(int j = 0; j < pegs[0].length; j++) {
				pegs[i][j] = new LitePegButton();
				pegs[i][j].addActionListener(listener);
				this.add(pegs[i][j]);
			}
		}
	}
	
	// resets color of all pegs
	public void reset() {
		
		for(int i = 0; i < pegs.length; i++) {
			for(int j = 0; j < pegs[0].length; j++) {
				pegs[i][j].resetColor();
			}
		}
	}

}
