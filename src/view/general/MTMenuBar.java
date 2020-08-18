package view.general;

import globals.GlobalConstants;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import language.Language;
import language.LanguageSetting;
import language.Messages;
import model.DataBase;
import task.SaveDataBaseTask;
import task.Task;
import task.WaitingDialog;
import view.graphicalElements.JCheckBoxMenuItemLingual;
import view.graphicalElements.JMenuHover;
import view.graphicalElements.JMenuItemLingual;

@SuppressWarnings("serial")
public class MTMenuBar extends JMenuBar implements ActionListener {
	
	private MainView mainView;

	public MTMenuBar(MainView mainView) {
		this.mainView = mainView;

		createMenuFile();
		createMenuEdit();
		createMenuLanguage();
		
		add(Box.createHorizontalGlue());
		add(new JLabel(GlobalConstants.COPYRIGHT + " snailhaus  "));
		
		
		// register menu items for this action listener
		for(Component c : getComponents()) {
			if(c instanceof JMenuHover) {
				JMenuHover menu = (JMenuHover) c;
				for(Component cmi : menu.getMenuComponents()) {
					if(cmi instanceof JMenuItem) {
						JMenuItem menuItem = (JMenuItem) cmi;
						menuItem.addActionListener(this);
					}
				}
			}
		}
	}
	
	private void createMenuFile() {
		JMenuHover menuFile = new JMenuHover("MenuBar.MenuFile");
		menuFile.setMnemonic(Messages.getString("Mnemonic.File").charAt(0));
		
		JMenuItemLingual menuItemNew  = new JMenuItemLingual("MenuBar.MenuItemNew");
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		menuFile.add(menuItemNew);
		
		JMenuItemLingual menuItemSave = new JMenuItemLingual("MenuBar.MenuItemSave");
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		menuFile.add(menuItemSave);

		menuFile.addSeparator();
		
		JMenuItemLingual menuItemInfo = new JMenuItemLingual("MenuBar.MenuItemInfo");
		menuItemInfo.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));
		menuFile.add(menuItemInfo);
		
		menuFile.addSeparator();
		
		JMenuItemLingual menuItemEnd  = new JMenuItemLingual("MenuBar.MenuItemEnd");
		menuItemEnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		menuFile.add(menuItemEnd);
		
		add(menuFile);
	}

	private void createMenuEdit() {
		JMenuHover menuEdit = new JMenuHover("MenuBar.MenuEdit");
		menuEdit.setMnemonic(Messages.getString("Mnemonic.Edit").charAt(0));
		
		JMenuItemLingual menuItemBoarder  = new JMenuItemLingual("MenuBar.MenuItemBoarder");
		menuItemBoarder.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(menuItemBoarder);
		
		JCheckBoxMenuItemLingual menuItemFitImage  = new JCheckBoxMenuItemLingual("MenuBar.MenuItemFitImage");
		menuItemFitImage.setSelected(false);
		menuItemFitImage.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(menuItemFitImage);
		
		add(menuEdit);
	}

	private void createMenuLanguage() {
		LanguageSetting lanSet = LanguageSetting.getInstance();
		Language lan = lanSet.getLanguage();
		
		JMenuHover menuLanguage = new JMenuHover("MenuBar.MenuLanguage");
		menuLanguage.setMnemonic(Messages.getString("Mnemonic.Language").charAt(0));
		
		JCheckBoxMenuItemLingual menuItemEnglish  = new JCheckBoxMenuItemLingual("MenuBar.MenuItemEnglish");
		menuItemEnglish.setSelected(lan.equals(Language.ENGLISH));
		menuItemEnglish.setAccelerator(KeyStroke.getKeyStroke('1', InputEvent.CTRL_DOWN_MASK));
		menuLanguage.add(menuItemEnglish);
		
		JCheckBoxMenuItemLingual menuItemGerman  = new JCheckBoxMenuItemLingual("MenuBar.MenuItemGerman");
		menuItemGerman.setSelected(lan.equals(Language.GERMAN));
		menuItemGerman.setAccelerator(KeyStroke.getKeyStroke('2', InputEvent.CTRL_DOWN_MASK));
		menuLanguage.add(menuItemGerman);
		
		add(menuLanguage);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("MenuBar.MenuItemNew")) {
			createNewFile();
			return;
		}
		if(actionCommand.equals("MenuBar.MenuItemSave")) {
			saveFile();
			return;
		}
		if(actionCommand.equals("MenuBar.MenuItemInfo")) {
			new DialogInfo(mainView);
			return;
		}
		if(actionCommand.equals("MenuBar.MenuItemEnd")) {
			mainView.endProgram();
		}
		
		if(actionCommand.equals("MenuBar.MenuItemBoarder")) {
			new DialogBoarder(mainView);
			return;
		}
		if(actionCommand.equals("MenuBar.MenuItemFitImage")) {
			if(e.getSource() instanceof JCheckBoxMenuItemLingual) {
				JCheckBoxMenuItemLingual item = (JCheckBoxMenuItemLingual) e.getSource();
				DataBase.getInstance().fitImage(item.isSelected());
			}
			return;
		}
		if(actionCommand.equals("MenuBar.MenuItemEnglish")) {
			changeLanguage(Language.ENGLISH, "MenuBar.MenuItemEnglish");
		}
		if(actionCommand.equals("MenuBar.MenuItemGerman")) {
			changeLanguage(Language.GERMAN, "MenuBar.MenuItemGerman");
		}
	}
	
	private void changeLanguage(Language newLan, String actionCommand) {
		

		// select the new menu item
		for(Component c : getComponents()) {
			if(c instanceof JMenuHover) {
				JMenuHover menu = (JMenuHover) c;
				for(Component cmi : menu.getMenuComponents()) {
					if(cmi instanceof JCheckBoxMenuItemLingual) {
						JCheckBoxMenuItemLingual menuItem = (JCheckBoxMenuItemLingual) cmi;
						menuItem.setSelected(menuItem.getActionCommand().equals(actionCommand));
					}
				}
			}
		}


		LanguageSetting lanSet = LanguageSetting.getInstance();
		if (lanSet.getLanguage().equals(newLan)) {
			// nothing to do
		}
		else {
			// change language
			lanSet.setLanguage(newLan);
			JOptionPane.showMessageDialog(mainView,	Messages.getString("LanguageChanged.InfoText"));
		}
	}
	
	
	public void createNewFile() {
		new DialogNew(mainView);
	}

	public void saveFile() {
		Task task = new SaveDataBaseTask(mainView);
		new WaitingDialog(task);
	}
}
