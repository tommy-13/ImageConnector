package view.general;

import globals.GlobalFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JTextField;

import view.graphicalElements.JColorButton;
import view.graphicalElements.JSpinnerRGB;
import language.Messages;

@SuppressWarnings("serial")
public class DialogColor extends JDialog implements ActionListener {
	
	private JButton okButton;
	private JButton cancelButton;
	private Color	returnColor;

	private JSpinnerRGB siRed;
	private JSpinnerRGB siGreen;
	private JSpinnerRGB siBlue;
	private JTextField	tfChosenCol = new JTextField();
	
	private final int		COL_COLS = 13;
	private final int		COL_ROWS = 9;
	private final int[][][] basicRGB = {
			{{51,0,0},	{102,0,0},   {153,0,0},   {204,0,0},   {255,0,0},     {255,51,51},   {255,102,102}, {255,153,153}, {255,204,204}},
			{{51,25,0}, {102,51,0},  {153,76,0},  {204,102,0}, {255,128,0},   {255,153,51},  {255,178,102}, {255,204,153}, {255,229,204}},
			{{51,51,0}, {102,102,0}, {153,153,0}, {204,204,0}, {255,255,0},   {255,255,51},  {255,255,102}, {255,255,153}, {255,255,204}},
			{{25,51,0}, {51,102,0},  {76,153,0},  {102,204,0}, {128,255,0},   {153,255,51},  {178,255,102}, {204,255,153}, {229,255,204}},
			{{0,51,0}, 	{0,102,0},   {0,153,0},   {0,204,0},   {0,255,0},     {51,255,51},   {102,255,102}, {153,255,153}, {204,255,204}},
			{{0,51,25}, {0,102,51},  {0,153,76},  {0,204,102}, {0,255,128},   {51,255,153},  {102,255,178}, {153,255,204}, {204,255,229}},
			{{0,51,51}, {0,102,102}, {0,153,153}, {0,204,204}, {0,255,255},   {51,255,255},  {102,255,255}, {153,255,255}, {204,255,255}},
			{{0,25,51}, {0,51,102},  {0,76,153},  {0,102,204}, {0,128,255},   {51,153,255},  {102,178,255}, {153,204,255}, {204,229,255}},
			{{0,0,51}, 	{0,0,102},   {0,0,153},   {0,0,204},   {0,0,255},     {51,51,255},   {102,102,255}, {153,153,255}, {204,204,255}},
			{{25,0,51}, {51,0,102},  {76,0,153},  {102,0,204}, {127,0,255},   {153,51,255},  {178,102,255}, {204,153,255}, {229,204,255}},
			{{51,0,51}, {102,0,102}, {153,0,153}, {204,0,204}, {255,0,255},   {255,51,255},  {255,102,255}, {255,153,255}, {255,204,255}},
			{{51,0,25}, {102,0,51},  {153,0,76},  {204,0,102}, {255,0,127},   {255,51,153},  {255,102,178}, {255,153,204}, {255,204,229}},
			{{0,0,0}, 	{32,32,32},  {64,64,64},  {92,92,92},  {128,128,128}, {160,160,160}, {192,192,192}, {224,224,224}, {255,255,255}}
	};
	private JColorButton[][] basicColors = new JColorButton[COL_COLS][COL_ROWS];
	
	
	public DialogColor(Component parent, Color currentColor) {
		
		returnColor = currentColor;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLayout(new GridBagLayout());
		
		/* set icon */
		setIconImages(GlobalFunctions.getIconImages());

		setTitle(Messages.getString("DialogColor.Title"));

		/* Basic colors */
		JPanel pBasicColors = createBasicColorPanel();
		GridBagConstraints gbc_pBasicColors = new GridBagConstraints();
		gbc_pBasicColors.gridx = 0;
		gbc_pBasicColors.gridy = 0;
		gbc_pBasicColors.gridheight = 2;
		gbc_pBasicColors.fill  = GridBagConstraints.BOTH;
		gbc_pBasicColors.insets = new Insets(10, 20, 0, 20);
		add(pBasicColors, gbc_pBasicColors);
		
		/* RGB */
		JPanel pRGB = createRGBPanel();
		GridBagConstraints gbc_pRGB = new GridBagConstraints();
		gbc_pRGB.gridx = 1;
		gbc_pRGB.gridy = 0;
		gbc_pRGB.fill  = GridBagConstraints.BOTH;
		gbc_pRGB.insets = new Insets(10, 0, 20, 20);
		add(pRGB, gbc_pRGB);
		
		/* Current Choice */
		JPanel pChoice = createChoicePanel();
		GridBagConstraints gbc_pChoice = new GridBagConstraints();
		gbc_pChoice.gridx = 1;
		gbc_pChoice.gridy = 1;
		gbc_pChoice.fill  = GridBagConstraints.BOTH;
		gbc_pChoice.insets = new Insets(0, 0, 0, 20);
		add(pChoice, gbc_pChoice);
		
		/* buttons */
		JPanel buttonContainer = createButtonContainer();
		GridBagConstraints gbc_buttonContainer = new GridBagConstraints();
		gbc_buttonContainer.gridx = 0;
		gbc_buttonContainer.gridy = 2;
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

	private JPanel createBasicColorPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black),
						Messages.getString("DialogColor.BasicColors")),
						BorderFactory.createEmptyBorder(0,0,0,0)));
		
		Dimension dim = new Dimension(24,24);
		for(int col=0; col<COL_COLS; col++) {
			for(int row=0; row<COL_ROWS; row++) {
				int[] rgb = basicRGB[col][row];
				basicColors[col][row] = new JColorButton(new Color(rgb[0], rgb[1], rgb[2]), dim);
				basicColors[col][row].addActionListener(this);
				panel.add(basicColors[col][row], getColorGBC(col, row));
			}
		}
		
		return panel;
	}
	private GridBagConstraints getColorGBC(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(1, 1, 1, 1);
		return gbc;
	}
	
	private JPanel createRGBPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black),
						Messages.getString("DialogColor.RGB")),
						BorderFactory.createEmptyBorder(0,0,0,0)));
		
		Dimension dim = GlobalFunctions.getSpinnerDimension();
		
		JLabel lRed = new JLabel(Messages.getString("DialogColor.Red"));
		panel.add(lRed, getGBC(0, true, 5, 0));
		
		siRed = new JSpinnerRGB(0, 255, this);
		siRed.setValue(returnColor.getRed());
		siRed.setPreferredSize(dim);
		panel.add(siRed, getGBC(1, false, 50, 0));
		
		JLabel lGreen = new JLabel(Messages.getString("DialogColor.Green"));
		panel.add(lGreen, getGBC(2, true, 5, 10));
		
		siGreen = new JSpinnerRGB(0, 255, this);
		siGreen.setValue(returnColor.getGreen());
		siGreen.setPreferredSize(dim);
		panel.add(siGreen, getGBC(3, false, 50, 0));
		
		JLabel lBlue = new JLabel(Messages.getString("DialogColor.Blue"));
		panel.add(lBlue, getGBC(4, true, 5, 10));
		
		siBlue = new JSpinnerRGB(0, 255, this);
		siBlue.setValue(returnColor.getBlue());
		siBlue.setPreferredSize(dim);
		panel.add(siBlue, getGBC(5, false, 50, 0));
		
		return panel;
	}
	private GridBagConstraints getGBC(int y, boolean west, int gapLeft, int gapTop) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.anchor = west ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		gbc.insets = new Insets(gapTop, gapLeft, 0, 5);
		return gbc;
	}
	
	
	private JPanel createChoicePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black),
						Messages.getString("DialogColor.CurrentChoice")),
						BorderFactory.createEmptyBorder(0,0,0,0)));
		
		tfChosenCol = new JTextField();
		tfChosenCol.setBackground(returnColor);
		tfChosenCol.setEditable(false);
		tfChosenCol.setFocusable(false);
		panel.add(tfChosenCol, BorderLayout.CENTER);
		
		return panel;
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
			returnColor = tfChosenCol.getBackground();
			dispose();
			return;
		}
		if(e.getSource() == cancelButton) {
			returnColor = null;
			dispose();
			return;
		}
		
		for(int col=0; col<COL_COLS; col++) {
			for(int row=0; row<COL_ROWS; row++) {
				if(e.getSource() == basicColors[col][row]) {
					Color color = basicColors[col][row].getColor();
					tfChosenCol.setBackground(color);
					siRed.setValue(color.getRed());
					siGreen.setValue(color.getGreen());
					siBlue.setValue(color.getBlue());
					return;
				}
			}
		}
	}
	
	
	public Color getReturnColor() {
		return returnColor;
	}
	
	
	/**
	 * Shows a dialog to choose a color. If the dialog was cancelled, <code>null</code>
	 * will be returned. Otherwise the chosen color will be returned.
	 * @param parent
	 * @param currentColor
	 * @return
	 */
	public static Color show(Component parent, Color currentColor) {
		DialogColor dialog = new DialogColor(parent, currentColor);
		return dialog.getReturnColor();
	}

	public void rgbChanged() {
		if(siRed == null || siGreen == null || siBlue == null) {
			return;
		}
		Color color = new Color(siRed.getInt(), siGreen.getInt(), siBlue.getInt());
		tfChosenCol.setBackground(color);
	}

}
