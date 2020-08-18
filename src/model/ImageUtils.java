package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	
	/**
	 * Loads an image to a buffered image.
	 * @param fileName name of the image file
	 * @return buffered image
	 */
	public static BufferedImage loadImage(String fileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Loads a buffered image with the single color <code>color</code>.
	 * @param color
	 * @param width	width of the image
	 * @param height height of the image
	 * @return buffered image of one color
	 */
	public static BufferedImage loadImage(Color color, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int colorValue		= color.getRGB();
		
		for(int col=0; col<width; col++) {
			for(int row=0; row<height; row++) {
				image.setRGB(col, row, colorValue);
			}
		}
		
		return image;
	}
	
	/**
	 * Tries to save <code>image</code> to a file with the name <code>fileName</code>. 
	 * @param fileName name of the new image file
	 * @param image buffered image
	 * @return true if saving was successful
	 */
	public static boolean saveImage(String fileName, BufferedImage image) {
		try {
			ImageIO.write(image, "jpg", new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Resizes an image to the size of
	 * <pre>width x height.</pre>
	 * @param image
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = scaled.createGraphics();
		g.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		
		return scaled;
	}
	
	/**
	 * Turns an image to a grayscale image
	 * @param image
	 * @return grayscale image
	 */
	public static BufferedImage makeGrayscaleImage(BufferedImage image) {
		int width	= image.getWidth();
		int height	= image.getHeight();
		BufferedImage newImage = new BufferedImage(
				width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}

	/**
	 * Resizes an image to a maximum of width <code>width</code> and height <code>height</code>.
	 * If necessary the color <code>color</code> will be used as background color.
	 * @param picture
	 * @param color
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage fitImage(BufferedImage picture, Color color, int width, int height) {
		// resize picture to fit
		float xDivisor = (float) picture.getWidth() / width;
		float yDivisor = (float) picture.getHeight() / height;
		int w, h;
		if(xDivisor > yDivisor) {
			w  = width;
			h = (int) (picture.getHeight() / xDivisor);
		}
		else {
			h = height;
			w  = (int) (picture.getWidth() / yDivisor);
		}
		picture = ImageUtils.resizeImage(picture, w, h);
		w = picture.getWidth();
		h = picture.getHeight();

		// write picture to color image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int colorValue		= color.getRGB();
		
		int xImgStart = (width - w) / 2;
		int xImgEnd	  = xImgStart + w;
		int yImgStart = (height - h) / 2;
		int yImgEnd	  = yImgStart + h;
		
		// write color pixel
		if(w == width) {
			for(int col=0; col<width; col++) {
				for(int row=0; row<yImgStart; row++) {
					image.setRGB(col, row, colorValue);
				}
				for(int row=yImgEnd; row<height; row++) {
					image.setRGB(col, row, colorValue);
				}
			}
		}
		else {
			for(int row=0; row<height; row++) {
				for(int col=0; col<xImgStart; col++) {
					image.setRGB(col, row, colorValue);
				}
				for(int col=xImgEnd; col<width; col++) {
					image.setRGB(col, row, colorValue);
				}
			}
		}
		
		// write picture
		for(int col=xImgStart; col<xImgEnd; col++) {
			for(int row=yImgStart; row<yImgEnd; row++) {
				image.setRGB(col, row, picture.getRGB(col-xImgStart, row-yImgStart));
			}
		}
		
		return image;
	}
	
}