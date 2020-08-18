package observer;

/**
 * Observer pattern - Data observable.
 * @author tommy
 *
 */
public interface DataObservable {
	
	public void registerObserver(DataObserver o);
	public void removeObserver(DataObserver o);
	public void notifyObserversNewImage(int x, int y);
	public void notifyObserversReseted();

}
