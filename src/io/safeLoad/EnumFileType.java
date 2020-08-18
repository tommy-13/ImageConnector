package io.safeLoad;

public enum EnumFileType {

	IMG_JPG;
	
	
	public static String getFileExtension(EnumFileType type) {
		switch(type) {
		case IMG_JPG: default: return "jpg";
		}
	}
}
