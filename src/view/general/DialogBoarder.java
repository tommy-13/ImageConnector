package view.general;

import globals.GlobalConstants;
import globals.GlobalFunctions;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.graphicalElements.JColorButton;
import view.graphicalElements.JSpinnerInteger;
import language.Messages;
import model.DataBase;

@SuppressWarnings("serial")
public class DialogBoarder extends JDialog implements ActionListener {
	
	private JButton okButton;
	private JButton cancelButton;

	private JSpinnerInteger siOuterVer;
	private JSpinnerInteger siOuterHor;
	private JColorButton	cbOuterCol;
	private JSpinnerInteger siInnerVer;
	private JSpinnerInteger siInnerHor;
	private JColorButton	cbInnerCol;
	
	
	public DialogBoarder(Component parent) {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLayout(new GridBagLayout());
		
		/* set icon */
		setIconImages(GlobalFunctions.getIconImages());

		setTitle(Messages.getString("DialogBoarder.Title"));

		JPanel pDimension = createOuterBoarderPanel();
		GridBagConstraints gbc_pDimension = new GridBagConstraints();
		gbc_pDimension.gridx = 0;
		gbc_pDimension.gridy = 0;
		gbc_pDimension.fill  = GridBagConstraints.BOTH;
		gbc_pDimension.insets = new Insets(10, 20, 0, 20);
		add(pDimension, gbc_pDimension);
		
		JPanel pSubPics = createInnerBoarderPanel();
		GridBagConstraints gbc_pSubPics = new GridBagConstraints();
		gbc_pSubPics.gridx = 1;
		gbc_pSubPics.gridy = 0;
		gbc_pSubPics.fill  = GridBagConstraints.BOTH;
		gbc_pSubPics.insets = new Insets(10, 0, 0, 20);
		add(pSubPics, gbc_pSubPics);
		
		JPanel buttonContainer = createButtonContainer();
		GridBagConstraints gbc_buttonContainer = new GridBagConstraints();
		gbc_buttonContainer.gridx = 0;
		gbc_buttonContainer.gridy = 1;
		gbc_buttonContainer.gridwidth = 2;
		gbc_buttonContainer.anchor = GridBagConstraints.EAST;
		gbc_buttonContainer.insets = new Insets(30, 20, 5, 20);
		add(buttonContainer, gbc_buttonContainer);
		
		getRootPane().setDefaultButton(okButton);
		pack();
		this.setResizable(false);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	
	private JPanel createInnerBoarderPanel() {
		DataBase db = DataBase.getInstance();

		JLabel lInnerVer = new JLabel(Messages.getString("Image.InnerBoarderVer") + ":");
		siInnerVer = new JSpinnerInteger(0, GlobalConstants.MAX_BOARDER);
		siInnerVer.setValue(db.getVerInnerBoarder());
		siInnerVer.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		JLabel lInnerHor = new JLabel(Messages.getString("Image.InnerBoarderHor") + ":");
		siInnerHor = new JSpinnerInteger(0, GlobalConstants.MAX_BOARDER);
		siInnerHor.setValue(db.getHorInnerBoarder());
		siInnerHor.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		JLabel lInnerCol = new JLabel(Messages.getString("Image.InnerColor") + ":");
		cbInnerCol = new JColorButton(db.getColInnerBoarder(), GlobalFunctions.getSpinnerDimension());
		cbInnerCol.addActionListener(this);
		
		return createBoarderPanel(Messages.getString("DialogBoarder.InnerBoarder"),
				lInnerVer, siInnerVer, lInnerHor, siInnerHor, lInnerCol, cbInnerCol);
	}
	
	private JPanel createOuterBoarderPanel() {
		DataBase db = DataBase.getInstance();

		JLabel lVer = new JLabel(Messages.getString("Image.OuterBoarderVer") + ":");
		siOuterVer = new JSpinnerInteger(0, GlobalConstants.MAX_BOARDER);
		siOuterVer.setValue(db.getVerOuterBoarder());
		siOuterVer.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		JLabel lHor = new JLabel(Messages.getString("Image.OuterBoarderHor") + ":");
		siOuterHor = new JSpinnerInteger(0, GlobalConstants.MAX_BOARDER);
		siOuterHor.setValue(db.getHorOuterBoarder());
		siOuterHor.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		JLabel lOuterCol = new JLabel(Messages.getString("Image.OuterColor") + ":");
		cbOuterCol = new JColorButton(db.getColOuterBoarder(), GlobalFunctions.getSpinnerDimension());
		cbOuterCol.addActionListener(this);
		
		return createBoarderPanel(Messages.getString("DialogBoarder.OuterBoarder"),
				lVer, siOuterVer, lHor, siOuterHor, lOuterCol, cbOuterCol);
	}
	
	private JPanel createBoarderPanel(String name, JLabel lbl1, JSpinnerInteger si1,
			JLabel lbl2, JSpinnerInteger si2, JLabel lbl3, JColorButton cb) {
		JPanel pDim = new JPanel();
		pDim.setLayout(new GridBagLayout());
		pDim.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black),
						name),
						BorderFactory.createEmptyBorder(0,0,0,0)));
		
		pDim.add(lbl1, getGBC(0, true, 5, 0));
		pDim.add(si1, getGBC(1, false, 50, 0));
		pDim.add(lbl2, getGBC(2, true, 5, 10));
		pDim.add(si2, getGBC(3, false, 50, 0));
		pDim.add(lbl3, getGBC(4, true, 5, 10));
		pDim.add(cb, getGBC(5, false, 50, 0));
		
		return pDim;
	}
	
	private GridBagConstraints getGBC(int y, boolean west, int gapLeft, int gapTop) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.anchor = west ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		gbc.insets = new Insets(gapTop, gapLeft, 0, 5);
		return gbc;
	}
	
	
	private JPanel createButtonContainer() {	
		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new GridBagLayout());
		
		okButton = new JButton(Messages.getString("Button.Ok"));
		okButton.addActionListener(this);
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.gridx = 0;
		gbc_okButton.gridy = 0;
		gbc_okButton.insets = new Insets(0, 0, 0, 10);
		buttonContainer.add(okButton, gbc_okButton);
		
		cancelButton = new JButton(Messages.getString("Button.Cancel"));
		cancelButton.addActionListener(this);
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridx = 1;
		gbc_cancelButton.gridy = 0;
		buttonContainer.add(cancelButton, gbc_cancelButton);
		
		return buttonContainer;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			DataBase db = DataBase.getInstance();
			db.setInnerBoarder(cbInnerCol.getColor(), siInnerHor.getInt(), siInnerVer.getInt());
			db.setOuterBoarder(cbOuterCol.getColor(), siOuterHor.getInt(), siOuterVer.getInt());
			dispose();
		}
		if(e.getSource() == cancelButton) {
			dispose();
		}
		
		if(e.getSource() == cbInnerCol) {
			Color col = DialogColor.show(this, cbInnerCol.getColor());
			if(col != null) {
				cbInnerCol.setColor(col);
			}
		}
		if(e.getSource() == cbOuterCol) {
			Color col = DialogColor.show(this, cbOuterCol.getColor());
			if(col != null) {
				cbOuterCol.setColor(col);
			}
		}
	}

}
