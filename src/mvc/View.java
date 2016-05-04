package mvc;

import debug.Debugger;
import debug.Supervisor;

/**
 * Diese Klasse ist des Basis Codeger�st f�r die Umsetzung der GUI-View's in diesem MVC Konzept.
 * View's m�ssen sich beim Modell registrieren, wenn sie bei �nderungen im Modell automatisch ge-refresht werden wollen.
 * Der Constructor dieser Klasse muss immer zuerst aufgerufen werden 
 * 
 * @author hugo-lucca
 */
public abstract class View implements ViewInterface
{
	private String name;
	private Controller myController;
	
	public View (String newName, Controller controller) {
		myController = controller;
		name   = newName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Controller getController() {
		if (myController != null) return myController;
		Supervisor.warnAndDebug(this, "view.getController(): no controller defined for this stage!");
		return null;
	}

	public void setData (String data)
	{
		Debugger.out("view.setData(): view '"+name+"' does not have a Model (try 'extends FXViewModel')");
	}
	
	public String getData()
	{
		Debugger.out("view.setData(): view '"+name+"' does not have a Model  (try 'extends FXViewModel')");
		return "";
	}
}
