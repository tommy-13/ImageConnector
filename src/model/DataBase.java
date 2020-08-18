package model;

import globals.GlobalConstants;
import io.safeLoad.SaveState;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import observer.DataObservable;
import observer.DataObserver;

public class DataBase implements DataObservable {
	
	private static DataBase uniqueDataBase = new DataBase();
	public static DataBase getInstance() {
		return uniqueDataBase;
	}
	
	// Maximal size of the mini pictures (the pictures in the program
	private final int			MAX_MINI_SIZE = 192;
	
	private ImageConnector 		imageConnector;
	private BufferedImage[][]	miniImages;
	private String[][]			imageFileNames;
	private Color[][]			imageColors;
	
	private int					picsX 		= 2;
	private int					picsY 		= 2;
	private int					subWidth	= 1152;
	private int					subHeight	= 864;
	private int					miniWidth;
	private int					miniHeight;
	
	private int					horizontalBoarderThickness		= 4;
	private int					verticalBoarderThickness		= 4;
	private Color				innerBoarderColor				= Color.blue;
	
	private int					horizontalOuterBoarderThickness	= 4;
	private int					verticalOuterBoarderThickness	= 4;
	private Color				outerBoarderColor				= Color.blue;
	
	private boolean				fitImage = false;
	
	
	private DataBase() {
		reset(picsX, picsY, subWidth, subHeight);
	}
	
	public int 		getPicsX()				{return picsX;}
	public int 		getPicsY()				{return picsY;}
	public int 		getSubWidth()			{return subWidth;}
	public int 		getSubHeight()			{return subHeight;}
	public int		getMiniWidth()			{return miniWidth;}
	public int		getMiniHeight()			{return miniHeight;}
	public int 		getHorOuterBoarder()	{return horizontalOuterBoarderThickness;}
	public int 		getVerOuterBoarder()	{return verticalOuterBoarderThickness;}
	public Color 	getColOuterBoarder()	{return outerBoarderColor;}
	public int		getHorInnerBoarder()	{return horizontalBoarderThickness;}
	public int		getVerInnerBoarder()	{return verticalBoarderThickness;}
	public Color	getColInnerBoarder()	{return innerBoarderColor;}

	public int getImageWidth() {
		return picsX * subWidth + 2 * verticalOuterBoarderThickness + (picsX - 1) * verticalBoarderThickness;
	}
	public int getImageHeight() {
		return picsY * subHeight + 2 * horizontalOuterBoarderThickness + (picsY - 1) * horizontalBoarderThickness;
	}
	
	public Color getColor(int picX, int picY) {
		return imageColors[picX][picY];
	}
	
	/**
	 * Calculates the dimension for the mini pictures.
	 */
	public void calculateMiniDimensions() {
		int 	max	= Math.max(subWidth, subHeight);
		if(max < MAX_MINI_SIZE) {
			miniWidth  = subWidth;
			miniHeight = subHeight;
		}
		else {
			float 	div	= (float) max / MAX_MINI_SIZE; 
			miniWidth 	= (int) (subWidth  / div);
			miniHeight 	= (int) (subHeight / div);
		}
	}
	
	public void setInnerBoarder(Color color, int horizontalThickness, int verticalThickness) {
		innerBoarderColor			= color;
		horizontalBoarderThickness	= horizontalThickness;
		verticalBoarderThickness	= verticalThickness;
		imageConnector.setInnerBoarderColor(color);
		imageConnector.setInnerBoarderThickness(horizontalThickness, verticalThickness);
	}
	
	public void setOuterBoarder(Color color, int horizontalThickness, int verticalThickness) {
		outerBoarderColor				= color;
		horizontalOuterBoarderThickness	= horizontalThickness;
		verticalOuterBoarderThickness	= verticalThickness;
		imageConnector.setOuterBoarderColor(color);
		imageConnector.setOuterBoarderThickness(horizontalThickness, verticalThickness);
	}
	
	public BufferedImage getMiniPic(int x, int y) {
		return miniImages[x][y];
	}

	/**
	 * If true, the image dimensions will keep their ratio.
	 * @param fitImage
	 */
	public void fitImage(boolean fitImage) {
		this.fitImage = fitImage;
	}
	
	public void reset(int picsX, int picsY, int subWidth, int subHeight) {
		this.picsX		= picsX;
		this.picsY		= picsY;
		this.subWidth	= subWidth;
		this.subHeight	= subHeight;
		calculateMiniDimensions();
		
		imageConnector	= new ImageConnector(picsX, picsY, subWidth, subHeight);
		miniImages		= new BufferedImage[picsX][picsY];
		imageFileNames	= new String[picsX][picsY];
		imageColors		= new Color[picsX][picsY];
		for(int col=0; col<picsX; col++) {
			for(int row=0; row<picsY; row++) {
				miniImages[col][row] = ImageUtils.resizeImage(
						imageConnector.getImage(col, row),
						miniWidth,
						miniHeight);
				imageColors[col][row] = GlobalConstants.DEFAULT_IMAGE_COLOR;
				imageFileNames[col][row] = null;
			}
		}
		
		setOuterBoarder(outerBoarderColor, horizontalOuterBoarderThickness, verticalOuterBoarderThickness);
		setInnerBoarder(innerBoarderColor, horizontalBoarderThickness, verticalBoarderThickness);
		
		SaveState.getInstance().setSavingNecessary(false);
		notifyObserversReseted();
	}
	
	/**
	 * Loads an image to the data base.
	 * @param picX x position of the picture
	 * @param picY y position of the picture
	 * @param path load path
	 */
	public void loadImage(int picX, int picY, String path) {
		if(fitImage) {
			imageConnector.loadSubImage(picX, picY, path, imageColors[picX][picY]);
		}
		else {
			imageConnector.loadSubImage(picX, picY, path);
		}
		
		imageFileNames[picX][picY] = path;
		miniImages[picX][picY] = ImageUtils.resizeImage(
				imageConnector.getImage(picX, picY),
				miniWidth,
				miniHeight);

		SaveState.getInstance().setSavingNecessary(true);
		notifyObserversNewImage(picX, picY);
	}
	
	/**
	 * Loads a color (the whole picture will be a single color).
	 * @param picX x position of the color
	 * @param picY y position of the color
	 * @param color
	 */
	public void loadImage(int picX, int picY, Color color) {
		if(fitImage && imageFileNames[picX][picY] != null) {
			imageConnector.loadSubImage(picX, picY, imageFileNames[picX][picY], color);
		}
		else {
			imageConnector.loadSubImage(picX, picY, color);
		}
		
		imageColors[picX][picY] = color;
		miniImages[picX][picY] = ImageUtils.resizeImage(
				imageConnector.getImage(picX, picY),
				miniWidth,
				miniHeight);
		
		SaveState.getInstance().setSavingNecessary(true);
		notifyObserversNewImage(picX, picY);
	}

	/**
	 * Saves the created image.
	 * @param path save path
	 */
	public void saveNewImage(String path) {
		imageConnector.setImageName(path);
		imageConnector.createImage();
		SaveState.getInstance().setSavingNecessary(false);
	}
	
	

	
	
	private List<DataObserver> dataObserver = new ArrayList<DataObserver>();
	
	@Override
	public void registerObserver(DataObserver o) {
		if(!dataObserver.contains(o)) {
			dataObserver.add(o);
		}
	}
	@Override
	public void removeObserver(DataObserver o) {
		if(dataObserver.contains(o)) {
			dataObserver.remove(o);
		}
	}

	@Override
	public void notifyObserversReseted() {
		for(DataObserver o : dataObserver) {
			o.fireReseted();
		}
	}

	@Override
	public void notifyObserversNewImage(int x, int y) {
		for(DataObserver o : dataObserver) {
			o.fireNewImage(x, y);
		}
	}
	
}
