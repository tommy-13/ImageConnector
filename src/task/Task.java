package task;

import java.awt.Component;

public interface Task {

	public void execute();
	public boolean isDone();
	public Component getParent();
	public String getTitle();
	public String getText();
	
}
