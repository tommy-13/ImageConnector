package view.general;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import observer.DataObserver;
import task.LoadColorTask;
import task.LoadDroppedImageTask;
import task.LoadImageTask;
import task.Task;
import task.WaitingDialog;
import model.DataBase;

@SuppressWarnings("serial")
public class ImageView extends JPanel implements DataObserver, MouseListener, DropTargetListener {
	
	private MainView		mainView;
	private Container		cImages;
	private ImagePanel[][] 	images;
	
	
	public ImageView(MainView mainView) {
		
		this.mainView = mainView;
		
		setLayout(new BorderLayout());
		
		DataBase.getInstance().registerObserver(this);
		
		/* basis */
		Container cPane = new Container();
		cPane.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(cPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		scrollPane.getVerticalScrollBar().setBlockIncrement(300);
		scrollPane.getHorizontalScrollBar().setBlockIncrement(300);
		scrollPane.setSize(2000, 1000);
		add(scrollPane, BorderLayout.CENTER);

		Container north = new Container();
		north.setLayout(new BorderLayout());
		cPane.add(north, BorderLayout.NORTH);
		
		cImages = new Container();
		cImages.setLayout(new GridBagLayout());
		north.add(cImages, BorderLayout.WEST);
		
		fireReseted();
	}
	
	
	@Override
	public void fireNewImage(int x, int y) {
		images[x][y].setImage(DataBase.getInstance().getMiniPic(x, y));
	}

	@Override
	public void fireReseted() {
		// remove MouseListener, DropTargetListener
		if(images != null) {
			for(ImagePanel[] is : images) {
				for(ImagePanel i : is) {
					i.removeMouseListener(this);
				}
			}
		}
		
		DataBase db	= DataBase.getInstance();
		images		= new ImagePanel[db.getPicsX()][db.getPicsY()];
		
		cImages.removeAll();
		cImages.validate();
		
		for(int col=0; col<db.getPicsX(); col++) {
			for(int row=0; row<db.getPicsY(); row++) {
				images[col][row] = new ImagePanel(db.getMiniPic(col, row), this);
				images[col][row].addMouseListener(this);
				
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = col;
				gbc.gridy = row;
				gbc.insets = new Insets(5, 5, 5, 5);
				cImages.add(images[col][row], gbc);
			}
		}
		validate();
		repaint();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		DataBase db = DataBase.getInstance();
		
		for(int col=0; col<db.getPicsX(); col++) {
			for(int row=0; row<db.getPicsY(); row++) {
				if(e.getSource() == images[col][row]) {
					switch(e.getButton()) {
					
					case MouseEvent.BUTTON1:
						Task task = new LoadImageTask(mainView, col, row);
						new WaitingDialog(task);
						break;
						
					case MouseEvent.BUTTON3:
						Task task1 = new LoadColorTask(mainView, col, row);
						new WaitingDialog(task1);
						break;
					}
					
					return;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Cursor.getDefaultCursor());
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	
	@Override
	public void drop(DropTargetDropEvent event) {
		boolean stop = false;

		DataBase db = DataBase.getInstance();
		int x = -1;
		int y = -1;
		for(int col=0; col<db.getPicsX(); col++) {
			for(int row=0; row<db.getPicsY(); row++) {
				if(event.getSource() == images[col][row].getDropTarget()) {
					x = col;
					y = row;
					stop = true;
					break;
				}
			}
			if(stop) {
				break;
			}
		}
		if(x == -1 || y == -1) {
			event.dropComplete(true);
			return;
		}
		stop = false;

		// Accept copy drops
		event.acceptDrop(DnDConstants.ACTION_COPY);
		// Get the transfer which can provide the dropped item data
		Transferable transferable = event.getTransferable();
		// Get the data formats of the dropped item
		DataFlavor[] flavors = transferable.getTransferDataFlavors();

		// Loop through the flavors
		for(DataFlavor flavor : flavors) {
			try {
				// If the drop items are files
				if(flavor.isFlavorJavaFileListType()) {
					// Get all of the dropped files
					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable.getTransferData(flavor);

					// Loop them through
					for(File file : files) {
						String path = file.getAbsolutePath();
						if(path.toLowerCase().endsWith(".jpg")) {
							Task task = new LoadDroppedImageTask(mainView, path, x, y);
							new WaitingDialog(task);
							stop = true;
							break;
						}
					}
				}
			} catch (Exception e) {e.printStackTrace();}

			if(stop) {
				break;
			}
		}

		// Inform that the drop is complete
		event.dropComplete(true);
	}
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {}
	@Override
	public void dragExit(DropTargetEvent dte) {}
	@Override
	public void dragOver(DropTargetDragEvent dtde) {}
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}

}
