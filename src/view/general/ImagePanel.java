package view.general;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	
	private BufferedImage	image;
	private DropTarget		dropTarget;
	
	public ImagePanel(BufferedImage image, DropTargetListener dtl) {
		super();
		this.image = image;
		dropTarget = new DropTarget(this, dtl);
		
		Dimension dim = new Dimension(image.getWidth(), image.getHeight());
		setMaximumSize(dim);
		setPreferredSize(dim);
		setMinimumSize(dim);
	}
	
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}


	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}
	
	public DropTarget getDropTarget() {
		return dropTarget;
	}
}
