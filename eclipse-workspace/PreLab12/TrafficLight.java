import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TrafficLight extends JPanel implements ActionListener{

	private JButton button;
	private int colorIndex;
	private final Color[] colors = {Color.GREEN, Color.YELLOW, Color.RED};
	
	private TrafficLight() {
		colorIndex = 0;
		
		setLayout(new GridLayout());
		button = new JButton();
		button.addActionListener(this);
		
		button.setBackground(colors[0]);
		this.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		colorIndex = (colorIndex + 1) % colors.length;
		button.setBackground(colors[colorIndex]);
		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Traffic Light");
		frame.setPreferredSize(new Dimension(200,200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficLight());
		frame.pack();
		frame.setVisible(true);

	}
}
