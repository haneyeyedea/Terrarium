import java.awt.Color;

import javax.swing.JButton;

// The mine field button class. Must extend JButton. Represents and manages the state of a single button in the MineFieldGrid
public class MineFieldButton extends JButton{

	private boolean pathBool = false;
	private boolean mineBool = false;
	private Color defaultColor = Color.WHITE;
	private Color pathColor;
	private boolean pathShow = false;
	private Color mineColor;
	private boolean mineShow = false;
	private Color statusColor;
	private boolean statusShow = false;
	private int mineStatusNum;
	
	/**
	 * Creates MineFieldButton
	 */
	public MineFieldButton() {
		this.setBackground(defaultColor);
		this.setOpaque(true);
		this.setBorderPainted(false);
	}
	/**
	 * Takes number of surrounding mines from MineFieldPanel and stores the value
	 * @param mineStatusNum
	 */
	public void setMineStatusNum(int mineStatusNum) {
		this.mineStatusNum = mineStatusNum;
	}
	/**
	 * Returns the number of surrounding mines
	 * @return mineStatusNum
	 */
	public int getMineStatusNum() {
		return mineStatusNum;
	}
	/**
	 * If the button is on the solution path, sets the path color
	 * @param pathColor
	 */
	public void setPathColor(Color pathColor) {
		this.pathColor = pathColor;
	}
	/**
	 * If the button is on the solution path, sets a 'true flag', and if it's not already an exposed button and 'show button' is activated,
	 * shows the path color
	 * @param pathShow
	 */
	public void setPathShow(boolean pathShow) {
		this.pathShow = pathShow;
		if(pathShow && !statusShow) {
			this.setBackground(pathColor);
		}
		else {this.setBackground(defaultColor);
		}
	}
	/**
	 * If the button is a mine, sets the mine color
	 * @param mineColor
	 */
	public void setMineColor(Color mineColor) {
		this.mineColor = mineColor;
	}
	/**
	 * If the button is a mine, sets a 'true flag', and if it's not already an exposed button and 'show mine' is activated,
	 * shows the mine color
	 * @param mineShow
	 */
	public void setMineShow(boolean mineShow) {
		this.mineShow = mineShow;
		if(mineShow && !statusShow) {
			this.setBackground(mineColor);
		}
		else {this.setBackground(defaultColor);
		}
	}
	public boolean getStatusShow() {
		return statusShow;
	}
	/**
	 * Sets the status color of the button depending on the number of surrounding mines
	 * @param statusColor
	 */
	public void setStatusColor(Color statusColor) {
		this.statusColor = statusColor;
	}
	public void setStatusShow() {
		statusShow = true;
		this.setBackground(statusColor);
	}
	public void resetStatus() {
		statusShow = false;
		statusColor = defaultColor;
	}
	public void setColor(Color color) {
		this.setBackground(color);
	}
	public void setPathBool(boolean pathBool){
		this.pathBool = pathBool;
	}
	public boolean getPathBool() {
		return pathBool;
	}
	public void setMineBool(boolean mineBool){
		this.mineBool = mineBool;
	}
	
	public boolean getMineBool() {
		return mineBool;
	}
	
}
