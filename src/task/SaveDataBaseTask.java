package task;

import io.safeLoad.EnumFileType;
import io.safeLoad.PathChooser;

import java.awt.Component;

import language.Messages;
import model.DataBase;

public class SaveDataBaseTask implements Task {
	
	private Component	parent;
	private boolean		isSaved = false;
	
	public SaveDataBaseTask(Component parent) {
		this.parent = parent;
	}
	
	
	
	@Override
	public boolean isDone() {
		return isSaved;
	}
	
	@Override
	public void execute() {
		String savePath = PathChooser.displayExportFileChooser(parent, EnumFileType.IMG_JPG);
		if(savePath != null) {
			DataBase.getInstance().saveNewImage(savePath);
			isSaved = true;
		}
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public String getTitle() {
		return Messages.getString("Task.SaveTitle");
	}

	@Override
	public String getText() {
		return Messages.getString("Task.SaveText");
	}

}
