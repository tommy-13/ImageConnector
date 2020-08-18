package model;

import globals.GlobalConstants;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageConnector {

	private String				imageName = "image";
	private int 				picsX, picsY;
	private int 				subWidth, subHeight;
	private BufferedImage[][] 	images;
	private BufferedImage		imageRES;
	
	private int					horizontalBoarderThickness;
	private int					verticalBoarderThickness;
	private int					innerBoarderColorRGB;
	private int					horizontalOuterBoarderThickness;
	private int					verticalOuterBoarderThickness;
	private int					outerBoarderColorRGB;
	
	
	/**
	 * Creates a new ImageConnector with <code>picsX</code> images in x-direction
	 * and <code>picsY</code> images in y-direction.<br>
	 * The dimensions of the single pictures will be
	 * <pre>subWidth x subHeight.</pre>
	 * @param picsX		number of pictures in x-direction
	 * @param picsY		number of pictures in y-direction
	 * @param subWidth	width of one picture
	 * @param subHeight height of one picture
	 */
	public ImageConnector(int picsX, int picsY, int subWidth, int subHeight) {
		this.picsX		= picsX;
		this.picsY		= picsY;
		this.images		= new BufferedImage[picsX][picsY];
		
		this.subWidth			= subWidth;
		if(subWidth <= 0) {
			subWidth = 64;
		}
		this.subHeight			= subHeight;
		if(subHeight <= 0) {
			subHeight = 64;
		}
		
		for(int col=0; col<picsX; col++) {
			for(int row=0; row<picsY; row++) {
				images[col][row] = ImageUtils.loadImage(GlobalConstants.DEFAULT_IMAGE_COLOR, subWidth, subHeight);
			}
		}
		
		setInnerBoarderColor(Color.BLACK);
		setOuterBoarderColor(Color.BLACK);
		setInnerBoarderThickness(4, 4);
		setOuterBoarderThickness(4, 4);
	}
	
	/**
	 * Sets the name for the new image. The new image will be saved as
	 * <code>imageName.jpg</code>.<br>
	 * The default name is <code>image.jpg</code>.
	 * @param imageName
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	/**
	 * Sets the thickness for the boarder between the pictures.
	 * @param horizontalThickness
	 * @param verticalThickness
	 */
	public void setInnerBoarderThickness(int horizontalThickness, int verticalThickness) {
		if(horizontalThickness < 0) {
			horizontalThickness = 0;
		}
		if(verticalThickness < 0) {
			verticalThickness = 0;
		}
		this.horizontalBoarderThickness	= horizontalThickness;
		this.verticalBoarderThickness	= verticalThickness;
	}
	
	/**
	 * Sets the color of the inner boarder
	 * @param color
	 */
	public void setInnerBoarderColor(Color color) {
		this.innerBoarderColorRGB = color.getRGB();
	}
	
	/**
	 * Sets the thickness for the surrounding boarder.
	 * @param horizontalThickness
	 * @param verticalThickness
	 */
	public void setOuterBoarderThickness(int horizontalThickness, int verticalThickness) {
		if(horizontalThickness < 0) {
			horizontalThickness = 0;
		}
		if(verticalThickness < 0) {
			verticalThickness = 0;
		}
		this.horizontalOuterBoarderThickness 	= horizontalThickness;
		this.verticalOuterBoarderThickness		= verticalThickness;
	}
	
	/**
	 * Sets the color of the outer boarder
	 * @param color
	 */
	public void setOuterBoarderColor(Color color) {
		this.outerBoarderColorRGB = color.getRGB();
	}
	
	/**
	 * Loads a subimage.
	 * @param x			x-position of the subimage
	 * @param y			y-position of the subimage
	 * @param fileName	image file name
	 */
	public void loadSubImage(int x, int y, String fileName) {
		BufferedImage image = ImageUtils.loadImage(fileName);
		if(image != null) {
			image = ImageUtils.resizeImage(image, subWidth, subHeight);
			images[x][y] = image;
		}
	}
	
	/**
	 * Loads a subimage in a single color.
	 * @param x 	x-position of the image
	 * @param y 	y-position of the image
	 * @param color color of the image
	 */
	public void loadSubImage(int x, int y, Color color) {
		BufferedImage image = ImageUtils.loadImage(color, subWidth, subHeight);
		images[x][y] = image;
	}
	
	/**
	 * Loads a subimage. The ratio of the image dimensions stay the same.
	 * As background the color <code>color</code> will be used.
	 * @param picX x-position of the image
	 * @param picY y-position of the image
	 * @param fileName path of the image file
	 * @param color background color
	 */
	public void loadSubImage(int picX, int picY, String fileName, Color color) {
		BufferedImage image = ImageUtils.loadImage(fileName);
		if(image != null) {
			image = ImageUtils.fitImage(image, color, subWidth, subHeight);
			images[picX][picY] = image;
		}
	}
	
	
	/**
	 * Creates the new image, if possible. It is only possible if all subimages
	 * has been loaded successfully before.
	 */
	public void createImage() {
		for(int i=0; i<picsX; i++) {
			for(int j=0; j<picsY; j++) {
				if(images[i][j] == null) {
					return;
				}
			}
		}
		connectImages();
		saveResult();
	}
	
	
	
	/**
	 * Saves the new images.
	 */
	private void saveResult() {
		ImageUtils.saveImage(imageName, imageRES);
	}
	
	/**
	 * Creates a new image by connecting all subimages.
	 */
	private void connectImages() {
		int width	= picsX * subWidth + (picsX - 1) * verticalBoarderThickness + 2 * verticalOuterBoarderThickness;
		int height	= picsY * subHeight + (picsY - 1) * horizontalBoarderThickness + 2 * horizontalOuterBoarderThickness;
		imageRES	= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		
		// left outerboarder
		for(int x=0; x<verticalOuterBoarderThickness; x++) {
			for(int y=0; y<height; y++) {
				imageRES.setRGB(x, y, outerBoarderColorRGB);
			}
		}
		// top outerboarder
		for(int x=verticalOuterBoarderThickness; x<width; x++) {
			for(int y=0; y<horizontalOuterBoarderThickness; y++) {
				imageRES.setRGB(x, y, outerBoarderColorRGB);
			}
		}
		
		
		for(int picX=0; picX < picsX; picX++) {
			int xOffset = picX * (subWidth + verticalBoarderThickness) + verticalOuterBoarderThickness;
			
			for(int picY=0; picY < picsY; picY++) {
				int yOffset = picY * (subHeight + horizontalBoarderThickness) + horizontalOuterBoarderThickness;
				
				// draw subimage
				BufferedImage currentImage = images[picX][picY];
				for(int x=0; x<subWidth; x++) {
					for(int y=0; y<subHeight; y++) {
						imageRES.setRGB(
								xOffset + x,
								yOffset + y,
								currentImage.getRGB(x, y));
					}
				}	
				// draw y boarder
				int boarderH = (picY < picsY-1) ? horizontalBoarderThickness : 0;
				for(int x=0; x<subWidth; x++) {
					for(int y=subHeight; y<subHeight+boarderH; y++) {
						imageRES.setRGB(
								xOffset + x,
								yOffset + y,
								innerBoarderColorRGB);
					}
				}
			}
			// draw x boarder
			int boarderW = (picX < picsX-1) ? verticalBoarderThickness : 0;
			for(int x=subWidth; x<subWidth+boarderW; x++) {
				for(int y=horizontalOuterBoarderThickness; y<height-horizontalOuterBoarderThickness; y++) {
					imageRES.setRGB(
							xOffset + x,
							y,
							innerBoarderColorRGB);
				}
			}
		}
		
		// right outer boarder
		for(int x=width-verticalOuterBoarderThickness; x<width; x++) {
			for(int y=0; y<height; y++) {
				imageRES.setRGB(x, y, outerBoarderColorRGB);
			}
		}
		// bottom outer boarder
		for(int x=0; x<width; x++) {
			for(int y=height-horizontalOuterBoarderThickness; y<height; y++) {
				imageRES.setRGB(x, y, outerBoarderColorRGB);
			}
		}
	}
	
	
	
	public BufferedImage getImage(int x, int y) {
		return images[x][y];
	}

}
