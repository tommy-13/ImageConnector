package task;

import java.awt.Color;
import java.awt.Component;
import view.general.DialogColor;
import language.Messages;
import model.DataBase;

public class LoadColorTask implements Task {
	
	private Component	parent;
	private int			picX, picY;
	private boolean		isLoaded = false;
	
	public LoadColorTask(Component parent, int picX, int picY) {
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
		DataBase db = DataBase.getInstance();
		Color color = DialogColor.show(parent, db.getColor(picX, picY));
		if(color != null) {
			db.loadImage(picX, picY, color);
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
