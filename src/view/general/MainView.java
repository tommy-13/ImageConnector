package view.general;

import globals.GlobalConstants;
import globals.GlobalFunctions;
import io.safeLoad.SaveState;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import observer.SavePathObserver;
import language.Messages;


@SuppressWarnings("serial")
public class MainView extends JFrame implements SavePathObserver {
	
	private JPanel 		mainPanel;
	private MTMenuBar 	menuBar;
	private ImageView	imageView;

	/* dimensions for window size */
	private Dimension basicWindowSize;
	private Dimension minimalWindowSize;


	public MainView() {
		
		/* model */
		SaveState.getInstance().registerObserver((SavePathObserver) this);

		/* save the basic window size */
		basicWindowSize = calculateBasicWindowDimension(GlobalConstants.SIZE_PERCENT);
		minimalWindowSize = calculateBasicWindowDimension(GlobalConstants.SIZE_PERCENT);


		/* initialize main frame */
		setProgramTitle();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {
				endProgram();
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}
		});


		/* set icon */
		setIconImages(GlobalFunctions.getIconImages());
		
		
		/* main Panel containing all except the menu */
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		
		/* menu bar */
		menuBar = new MTMenuBar(this);
		setJMenuBar(menuBar);
		
		/* panel for input and change of data */
		imageView = new ImageView(this);
		mainPanel.add(imageView, BorderLayout.CENTER);
		
		
		/* set size of window */
		setMinimumSize(minimalWindowSize);
		setSize(basicWindowSize);
		//mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		/* center window on screen */
		setLocationRelativeTo(null);
		setBackground(GlobalConstants.BACKGROUND);
		setVisible(true);
	}



	/**
	 * Calculate the dimension of the visible screen and set the window
	 * size to a percentage of this value. The visible screen is the screen
	 * without the task bar(s).
	 * @param screenPercentage percentage of the visible screen
	 * @return dimension of the visible screen
	 */
	public Dimension calculateBasicWindowDimension(double screenPercentage) {
		/* get screen size */
		Dimension _screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

		/* visible rectangle of screen */
		Rectangle _maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		/* calculate borders */
		Insets _screenInsets = new Insets(
				(int)_maxBounds.getY(),
				(int)_maxBounds.getX(),
				(int)(_screenDimension.getHeight() - _maxBounds.getY() - _maxBounds.getHeight()),
				(int)(_screenDimension.getWidth() - _maxBounds.getWidth() - _maxBounds.getX()));

		/* dimension of visible screen */
		int width = (int)(_screenDimension.getWidth() - _screenInsets.right - _screenInsets.left);
		int height = (int)(_screenDimension.getHeight() - _screenInsets.top - _screenInsets.bottom);

		Dimension _screenDimensionView = new Dimension((int) (width * screenPercentage),
				(int) (height * screenPercentage));

		return _screenDimensionView;
	}
	
	

	

	public void repaintScreen() {
		validate();
		repaint();
	}


	public void endProgram() {
		int answer = JOptionPane.OK_OPTION;
		if(SaveState.getInstance().isSavingNecessary()) {
			answer = JOptionPane.showConfirmDialog(this,
					Messages.getString("EndProgram.ConfirmText"),
					Messages.getString("EndProgram.ConfirmTitle"),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
		if(answer == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}



	@Override
	public void fireChangedSavePath() {
		setProgramTitle();
	}
	
	private void setProgramTitle() {
		SaveState saveState = SaveState.getInstance();
		if(saveState.hasSavePath()) {
			setTitle(Messages.getString("Program.Title") + " (" + saveState.getFileName() + ")");
		}
		else {
			setTitle(Messages.getString("Program.Title"));
		}
	}

}
