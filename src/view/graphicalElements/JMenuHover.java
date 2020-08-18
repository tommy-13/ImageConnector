package view.graphicalElements;

import globals.OSInformation;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;

import language.Messages;

@SuppressWarnings("serial")
public class JMenuHover extends JMenu implements MouseListener {
	
	private final Color HOVER = Color.blue;
	private final Color NORMAL = Color.black;
	
	public JMenuHover(String languageKey) {
		super();
		super.setActionCommand(languageKey);
		setText(Messages.getString(languageKey));
		if(!OSInformation.nimbusLookAndFeel) {
			this.addMouseListener(this);
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == this) {
			setForeground(HOVER);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == this) {
			resetView();
		}
	}
	
	public void resetView() {
		setForeground(NORMAL);
	}

}
