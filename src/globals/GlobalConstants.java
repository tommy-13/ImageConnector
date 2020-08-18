package globals;

import java.awt.Color;

public class GlobalConstants {

	// Minimal size of the window in percent of the whole screen
	public static final double SIZE_PERCENT = 0.7;
	
	// path of the icons for the program
	public static final String iconPath64 = "res/icon64.png";
	public static final String iconPath32 = "res/icon32.png";
	public static final String iconPath16 = "res/icon16.png";
	
	// signs
	public static final String COPYRIGHT = "\u00a9";

	// limits
	public static final int MAX_COLUMNS 	= 99;
	public static final int MAX_ROWS		= 99;
	public static final int MIN_SUBWIDTH	= 16;
	public static final int MAX_SUBWIDTH	= 99999;
	public static final int	MIN_SUBHEIGHT	= 16;
	public static final int MAX_SUBHEIGHT	= 99999;
	public static final int MAX_BOARDER		= 999;

	// colors
	public static final Color BACKGROUND   			= Color.LIGHT_GRAY;
	public static final Color DEFAULT_IMAGE_COLOR 	= new Color(255, 128, 0);
	
    // languages
	public static final int ID_ENGLISH = 0;
	public static final int ID_GERMAN  = 1;
}
