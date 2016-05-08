package mvc;

/**
 * Dieses Interface definiert die zwingenden Methoden f�r eine Klasse vom Typ Controller
 * f�r dieses MVC Konzept.
 * 
 * @author hugo-lucca
 */
public interface ControllerInterface
{
	public abstract void initMyModels();
	public abstract void initMyViews();
	
	public abstract Model getModel (String name);
	public abstract void showMainView ();
	public abstract View getView (String name);
}
