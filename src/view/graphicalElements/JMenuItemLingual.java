package view.graphicalElements;

import javax.swing.JMenuItem;

import language.Messages;

@SuppressWarnings("serial")
public class JMenuItemLingual extends JMenuItem {

	public JMenuItemLingual(String languageKey) {
		super();
		super.setActionCommand(languageKey);
		setText(Messages.getString(languageKey));
	}
}
