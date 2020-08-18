package task;

import java.awt.Component;
import language.Messages;
import model.DataBase;

public class LoadDroppedImageTask implements Task {
	
	private Component	parent;
	private int			picX, picY;
	private String		path;
	private boolean		isLoaded = false;
	
	public LoadDroppedImageTask(Component parent, String path, int picX, int picY) {
		this.parent = parent;
		this.path	= path;
		this.picX 	= picX;
		this.picY	= picY;
	}
	
	
	
	@Override
	public boolean isDone() {
		return isLoaded;
	}
	
	@Override
	public void execute() {
		if(path != null) {
			DataBase.getInstance().loadImage(picX, picY, path);
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
