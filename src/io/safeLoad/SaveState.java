package io.safeLoad;

import java.util.ArrayList;
import java.util.List;

import observer.SavePathObservable;
import observer.SavePathObserver;
import globals.OSInformation;

/**
 * The save state of the programm is recorded.
 * Only one instance of this class can be generated.
 * Only static access is possible to get this instance. 
 * @author tommy
 *
 */
public class SaveState implements SavePathObservable {

	private static SaveState uniqueSaveState = new SaveState();
	public static SaveState getInstance() {
		return uniqueSaveState;
	}
	private SaveState() {}
	
	
	private boolean savingNecessary	= false;
	private String 	savePath		= null;
	
	public void reset() {
		savingNecessary = false;
		savePath		= null;
		notifyObserversSavePathChange();
	}
	
	public void setSavePath(String savePath) {
		this.savePath = savePath;
		notifyObserversSavePathChange();
	}
	public String getSavePath() {
		return savePath;
	}
	public String getFileName() {
		if(savePath == null) {
			return null;
		}
		else {
			int index = savePath.lastIndexOf(OSInformation.fileSeparator);
			return savePath.substring(index+1);
		}
	}
	public boolean hasSavePath() {
		return savePath != null;
	}
	
	public void setSavingNecessary(boolean savingNecessary) {
		this.savingNecessary = savingNecessary;
	}
	public boolean isSavingNecessary() {
		return savingNecessary;
	}
	
	
	

	private List<SavePathObserver> savePathObserver = new ArrayList<SavePathObserver>();
	
	@Override
	public void registerObserver(SavePathObserver o) {
		savePathObserver.add(o);
	}
	@Override
	public void removeObserver(SavePathObserver o) {
		savePathObserver.remove(o);
	}
	@Override
	public void notifyObserversSavePathChange() {
		for(SavePathObserver spo : savePathObserver) {
			spo.fireChangedSavePath();
		}
	}
	
}
