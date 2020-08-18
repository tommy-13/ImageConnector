package view.graphicalElements;

import javax.swing.JCheckBoxMenuItem;
import language.Messages;

@SuppressWarnings("serial")
public class JCheckBoxMenuItemLingual extends JCheckBoxMenuItem {

	public JCheckBoxMenuItemLingual(String languageKey) {
		super();
		super.setActionCommand(languageKey);
		setText(Messages.getString(languageKey));
	}
}
