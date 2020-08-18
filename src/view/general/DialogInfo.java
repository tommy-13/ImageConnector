package view.general;

import globals.GlobalFunctions;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import language.Messages;
import model.DataBase;

@SuppressWarnings("serial")
public class DialogInfo extends JDialog {
	
	public DialogInfo(Component parent) {
		
		DataBase db = DataBase.getInstance();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLayout(new GridBagLayout());
		
		/* set icon */
		setIconImages(GlobalFunctions.getIconImages());
		setTitle(Messages.getString("DialogInfo.Title"));

		
		int leftGap = 20;
		int topGap	= 10;
		int rightGap = 20;
		int rightBetween = 30;
		
		addLabel(Messages.getString("DialogInfo.Width"), true, GridBagConstraints.CENTER,
				1, 0, 0, topGap, rightBetween, 0);
		addLabel(Messages.getString("DialogInfo.Height"), true, GridBagConstraints.CENTER,
				2, 0, 0, topGap, rightGap, 0);
		
		addLabel(Messages.getString("DialogInfo.SubPictures"), true, GridBagConstraints.WEST,
				0, 1, leftGap, 10, rightBetween, 0);
		addLabel(db.getPicsX() + "", false, GridBagConstraints.EAST,
				1, 1, 0, 10, rightBetween, 0);
		addLabel(db.getPicsY() + "", false, GridBagConstraints.EAST,
				2, 1, 0, 10, rightGap, 0);
		
		addLabel(Messages.getString("DialogInfo.SubSize"), true, GridBagConstraints.WEST,
				0, 2, leftGap, 5, rightBetween, 0);
		addLabel(db.getSubWidth() + "", false, GridBagConstraints.EAST,
				1, 2, 0, 5, rightBetween, 0);
		addLabel(db.getSubHeight() + "", false, GridBagConstraints.EAST,
				2, 2, 0, 5, rightGap, 0);
		
		addLabel(Messages.getString("DialogInfo.OuterBoarder"), true, GridBagConstraints.WEST,
				0, 3, leftGap, 5, rightBetween, 0);
		addLabel(db.getVerOuterBoarder() + "", false, GridBagConstraints.EAST,
				1, 3, 0, 5, rightBetween, 0);
		addLabel(db.getHorOuterBoarder() + "", false, GridBagConstraints.EAST,
				2, 3, 0, 5, rightGap, 0);
		
		addLabel(Messages.getString("DialogInfo.InnerBoarder"), true, GridBagConstraints.WEST,
				0, 4, leftGap, 5, rightBetween, 0);
		addLabel(db.getVerInnerBoarder() + "", false, GridBagConstraints.EAST,
				1, 4, 0, 5, rightBetween, 0);
		addLabel(db.getHorInnerBoarder() + "", false, GridBagConstraints.EAST,
				2, 4, 0, 5, rightGap, 0);
		
		addLabel(Messages.getString("DialogInfo.Image"), true, GridBagConstraints.WEST,
				0, 5, leftGap, 10, rightBetween, 0);
		addLabel(db.getImageWidth() + "", false, GridBagConstraints.EAST,
				1, 5, 0, 10, rightBetween, 0);
		addLabel(db.getImageHeight() + "", false, GridBagConstraints.EAST,
				2, 5, 0, 10, rightGap, 0);

		JButton okButton = new JButton(Messages.getString("Button.Ok"));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.gridx = 0;
		gbc_okButton.gridy = 6;
		gbc_okButton.gridwidth = 3;
		gbc_okButton.anchor = GridBagConstraints.CENTER;
		gbc_okButton.insets = new Insets(30, 20, 10, 10);
		add(okButton, gbc_okButton);
		
		getRootPane().setDefaultButton(okButton);
		pack();
		this.setResizable(false);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	private void addLabel(String text, boolean head, int anchor, int x, int y,
			int gapLeft, int gapTop, int gapRight, int gapBottom) {
		JLabel lbl = new JLabel(text);
		if(head) {
			Font font = lbl.getFont();
			lbl.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize()));
		}
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = anchor;
		gbc.insets = new Insets(gapTop, gapLeft, gapBottom, gapRight);
		add(lbl, gbc);
	}
}
