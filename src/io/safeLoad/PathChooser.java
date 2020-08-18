package io.safeLoad;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import language.Messages;

public class PathChooser {

	/* current directory */
	public static String DIRECTORY = null;
	public static final String DATA_FILE_STRING = EnumFileType.getFileExtension(EnumFileType.IMG_JPG);
	private static final FileFilter DATA_FILTER = new FileNameExtensionFilter(DATA_FILE_STRING, DATA_FILE_STRING);
	
	
	/**
	 * Displays a file chooser for saving/exporting.
	 * @param parent	parent component of the file chooser
	 * @param fileType	file type for the file to save/export
	 * @return			save/export path or null
	 */
	public static String displayExportFileChooser(Component parent, EnumFileType fileType) {
		
		/* choose file destination */
		@SuppressWarnings("serial")
		JFileChooser fileChooser = new JFileChooser(DIRECTORY){
			@Override
			public void approveSelection(){
				File f = getSelectedFile();
				String fName = f.getAbsolutePath();
				String extension = getFileFilter().getDescription();
				
				if(!fName.toLowerCase().endsWith("." + extension)) {
					fName += "." + extension;
				}
				
				
				if(new File(fName).exists()) {
					int result = JOptionPane.showConfirmDialog(
							this,
							Messages.getString("PathChooser.ConfirmFileOverwriteText"),
							Messages.getString("PathChooser.ConfirmFileOverwriteTitle"),
							JOptionPane.OK_CANCEL_OPTION);
					switch(result){
					case JOptionPane.OK_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
			super.approveSelection();
			}
		};
		
		
		fileChooser.setFileFilter(DATA_FILTER);
		String chooserTitle = Messages.getString("PathChooser.TitleSaveDataBase");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		
		int returnValue = fileChooser.showDialog(parent, chooserTitle);
		//DIRECTORY = fileChooser.getCurrentDirectory().getAbsolutePath();
		
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String fileName = selectedFile.getAbsolutePath();
			String extension = fileChooser.getFileFilter().getDescription();
			
			if(!fileName.toLowerCase().endsWith("." + extension)) {
				fileName += "." + extension;
			}
			
			DIRECTORY = fileName;
			return fileName;
		}
		return null;
	}
	
	
	/**
	 * Displays an file chooser for loading/importing a file.
	 * @param parent	parent component for this chooser
	 * @param fileType	loading/importing file type
	 * @return			the file which was loaded/imported
	 */
	public static File displayImportFileChooser(Component parent, EnumFileType fileType) {
		
		FileFilter fileFilter = DATA_FILTER;
		String chooserTitle = Messages.getString("PathChooser.TitleLoadDataBase");
		
		/* choose file */
		JFileChooser fileChooser = new JFileChooser(DIRECTORY);
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int returnValue = fileChooser.showDialog(parent, chooserTitle);
		DIRECTORY = fileChooser.getCurrentDirectory().getAbsolutePath();
		
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

}
