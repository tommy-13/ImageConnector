package observer;

/**
 * Observer pattern - Data observer.
 * @author tommy
 *
 */
public interface DataObserver {

	public void fireNewImage(int x, int y);
	public void fireReseted();

}
