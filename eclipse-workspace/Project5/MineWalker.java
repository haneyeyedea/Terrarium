import javax.swing.JFrame;

// The driver class. Creates the JFrame and add the MineWalkerPanel to the frame
public class MineWalker {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Mine Walker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MineWalkerPanel());
		frame.pack();
		frame.setVisible(true);

	}

}
