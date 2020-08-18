package task;

import io.safeLoad.EnumFileType;
import io.safeLoad.PathChooser;

import java.awt.Component;
import java.io.File;

import language.Messages;
import model.DataBase;

public class LoadImageTask implements Task {
	
	private Component	parent;
	private int			picX, picY;
	private boolean		isLoaded = false;
	
	public LoadImageTask(Component parent, int picX, int picY) {
		this.parent = parent;
		this.picX 	= picX;
		this.picY	= picY;
	}
	
	
	
	@Override
	public boolean isDone() {
		return isLoaded;
	}
	
	@Override
	public void execute() {
		File loadedFile = PathChooser.displayImportFileChooser(parent, EnumFileType.IMG_JPG);
		if(loadedFile != null) {
			String loadPath = loadedFile.getAbsolutePath();
			DataBase.getInstance().loadImage(picX, picY, loadPath);
			isLoaded = true;
		}
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public String getTitle() {
		return Messages.getString("Task.LoadImageTitle");
	}

	@Override
	public String getText() {
		return Messages.getString("Task.LoadImageText");
	}

}
