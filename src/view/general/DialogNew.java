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

import task.ResetTask;
import task.Task;
import task.WaitingDialog;
import view.graphicalElements.JSpinnerInteger;
import language.Messages;
import model.DataBase;

@SuppressWarnings("serial")
public class DialogNew extends JDialog implements ActionListener {
	
	private JButton okButton;
	private JButton cancelButton;

	private JSpinnerInteger siPicsX;
	private JSpinnerInteger siPicsY;
	private JSpinnerInteger siSubWidth;
	private JSpinnerInteger siSubHeight;
	
	
	public DialogNew(Component parent) {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLayout(new GridBagLayout());
		
		/* set icon */
		setIconImages(GlobalFunctions.getIconImages());

		setTitle(Messages.getString("DialogNew.Title"));

		JPanel pDimension = createDimensionPanel();
		GridBagConstraints gbc_pDimension = new GridBagConstraints();
		gbc_pDimension.gridx = 0;
		gbc_pDimension.gridy = 0;
		gbc_pDimension.fill  = GridBagConstraints.BOTH;
		gbc_pDimension.insets = new Insets(10, 20, 0, 20);
		add(pDimension, gbc_pDimension);
		
		JPanel pSubPics = createSubPicsPanel();
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
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		getRootPane().setDefaultButton(okButton);
		pack();
		this.setResizable(false);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	
	private JPanel createSubPicsPanel() {
		DataBase db = DataBase.getInstance();

		JLabel lWidth = new JLabel(Messages.getString("Image.SubWidth"));
		siSubWidth = new JSpinnerInteger(GlobalConstants.MIN_SUBWIDTH, GlobalConstants.MAX_SUBWIDTH);
		siSubWidth.setValue(db.getSubWidth());
		siSubWidth.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		JLabel lHeight = new JLabel(Messages.getString("Image.SubHeight"));
		siSubHeight = new JSpinnerInteger(GlobalConstants.MIN_SUBHEIGHT, GlobalConstants.MAX_SUBHEIGHT);
		siSubHeight.setValue(db.getSubHeight());
		siSubHeight.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		return createBoarderPanel(Messages.getString("DialogNew.SubDimension"), lWidth, siSubWidth, lHeight, siSubHeight);
	}
	
	private JPanel createDimensionPanel() {
		DataBase db = DataBase.getInstance();

		JLabel lHor = new JLabel(Messages.getString("Image.Columns"));
		siPicsX = new JSpinnerInteger(1, GlobalConstants.MAX_COLUMNS);
		siPicsX.setValue(db.getPicsX());
		siPicsX.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		JLabel lVer = new JLabel(Messages.getString("Image.Rows"));
		siPicsY = new JSpinnerInteger(1, GlobalConstants.MAX_ROWS);
		siPicsY.setValue(db.getPicsY());
		siPicsY.setPreferredSize(GlobalFunctions.getSpinnerDimension());
		
		return createBoarderPanel(Messages.getString("DialogNew.Dimension"), lHor, siPicsX, lVer, siPicsY);
	}
	
	private JPanel createBoarderPanel(String name, JLabel lbl1, JSpinnerInteger si1,
			JLabel lbl2, JSpinnerInteger si2) {
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
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.gridx = 0;
		gbc_okButton.gridy = 0;
		gbc_okButton.insets = new Insets(0, 0, 0, 10);
		buttonContainer.add(okButton, gbc_okButton);
		
		cancelButton = new JButton(Messages.getString("Button.Cancel"));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridx = 1;
		gbc_cancelButton.gridy = 0;
		buttonContainer.add(cancelButton, gbc_cancelButton);
		
		return buttonContainer;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			int picsX = siPicsX.getInt();
			int picsY = siPicsY.getInt();
			int subW  = siSubWidth.getInt();
			int subH  = siSubHeight.getInt();
			Task task = new ResetTask(this, picsX, picsY, subW, subH);
			new WaitingDialog(task);
			dispose();
		}
		if(e.getSource() == cancelButton) {
			dispose();
		}
	}

}
