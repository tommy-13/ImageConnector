package observer;

/**
 * Observer pattern - Save path observable.
 * @author tommy
 *
 */
public interface SavePathObservable {
	
	public void registerObserver(SavePathObserver o);
	public void removeObserver(SavePathObserver o);
	public void notifyObserversSavePathChange();

}
