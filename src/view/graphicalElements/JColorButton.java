package view.graphicalElements;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JColorButton extends JButton {
	
	private Color		color;
	
	public JColorButton(Color color, Dimension dim) {
		super();
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		setColor(color);
	}
	
	public void setColor(Color color) {
		this.color = color;
		setBackground(color);
	}
	
	public Color getColor() {
		return color;
	}
}
