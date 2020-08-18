package task;

import java.awt.Component;

import language.Messages;
import model.DataBase;

public class ResetTask implements Task {
	
	private Component	parent;
	private int			picsX;
	private int			picsY;
	private int			subWidth;
	private int			subHeight;
	
	public ResetTask(Component parent, int picsX, int picsY, int subWidth, int subHeight) {
		this.parent 	= parent;
		this.picsX		= picsX;
		this.picsY		= picsY;
		this.subWidth	= subWidth;
		this.subHeight	= subHeight;
	}
	
	
	
	@Override
	public boolean isDone() {
		return true;
	}
	
	@Override
	public void execute() {
		DataBase.getInstance().reset(picsX, picsY, subWidth, subHeight);
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public String getTitle() {
		return Messages.getString("Task.ResetTitle");
	}

	@Override
	public String getText() {
		return Messages.getString("Task.ResetText");
	}

}
