package start;

import globals.OSInformation;

import java.awt.Color;
import java.util.Locale;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import language.LanguageSetting;
import language.Messages;
import view.general.MainView;


public class ImageConnectorStarter {

	public static void main(String[] args) {

		/* set language */
		Messages.setLanguage(LanguageSetting.getInstance().getLanguage());
		switch(LanguageSetting.getInstance().getLanguage()) {
		case ENGLISH: default: Locale.setDefault(Locale.ENGLISH); break;
		case GERMAN: Locale.setDefault(Locale.GERMAN); break;	
		}
		
		//Messages.setLanguage(Language.GERMAN);
		//Locale.setDefault(Locale.GERMAN);

		
		// set look and and feel
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			OSInformation.nimbusLookAndFeel = false;

			/* set layout */
			UIManager.put("OptionPane.buttonPadding", 30);
			UIManager.put("OptionPane.sameSizeButtons", true);
			UIManager.put("Menu.selectionBackground", Color.black);
			UIManager.put("Menu.selectionForeground", Color.white);
			UIManager.put("MenuItem.selectionBackground", Color.black);
			UIManager.put("MenuItem.selectionForeground", Color.white);
			UIManager.put("MenuItem.acceleratorSelectionForeground", Color.white);
			UIManager.put("MenuItem.acceleratorForeground", Color.white);

			UIDefaults ui = UIManager.getLookAndFeelDefaults();
			ui.put("Menu.selectionBackground", Color.black);
			ui.put("PopupMenu.background", Color.LIGHT_GRAY);
			ui.put("Menu.background", Color.LIGHT_GRAY);
			ui.put("Menu.opaque", true);
			ui.put("MenuItem.background", Color.LIGHT_GRAY);
			ui.put("MenuItem.opaque", true);
			ui.put("PopupMenu.contentMargins", null);
		}
		
		new MainView();
	}

}
