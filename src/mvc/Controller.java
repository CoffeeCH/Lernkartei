package mvc;

import java.util.ArrayList;
import java.util.Iterator;

import debug.Debugger;
import debug.Supervisor;
import javafx.stage.Stage;
/**
 * Diese Klasse ist des Basis Codeger�st f�r die Kontrolle View's und Model's.
 * Sie bietet die Naviagtion zum n�chsten View an und die Suche nach einem bestimmten Modell.
 * 
 * @author hugo-lucca
 */
public abstract class Controller implements ControllerInterface
{
	private final ArrayList<View> views	= new ArrayList<View>();
	private final ArrayList<Model> models = new ArrayList<Model>();
	private String mainViewName;
	private FXSettings theFXSettings;
	
	public Controller (Stage primaryStage, FXSettings newFXSettings)
	{
		setMainViewName("main");
		this.theFXSettings = newFXSettings;
		if (this.theFXSettings == null) {
			theFXSettings = new FXSettings();
		}
		if (primaryStage == null) {
			primaryStage = new Stage();
		}
		getTheFXSettings().setPrimaryStage(primaryStage);
	
		initMyModels(); // Zuerst Model kreieren, 
		initMyViews();	// dann die View's
	}

	public Model getModel (String name)
	{
		for (Model m : getModels())
		{
			if (m.getName().equals(name)) { return m; }
		}

		if (name != null)
			Supervisor.warnAndDebug(this, "model(" + name + ") not found!");
		else
			Supervisor.warnAndDebug(this, "model(null) not allowed!");

		return null; // not found
	}

	public void showMainView ()
	{
		getView(getMainViewName()).show();
	}

	public View getView (String name)
	{
		for (View v : getViews())
		{
			if (v.getName().equals(name))
			{
				return v;
			}
		}
		if (name != null)
			Supervisor.warnAndDebug(this, "show("+name+") not found!");
		else
			Supervisor.warnAndDebug(this, "show(null) not allowed!");
		return null; // not found
	}

	public boolean addUniqueModel (Model newModel) {
		Iterator<Model> it = getModels().iterator();
		int found = 0;
		while (it.hasNext()) {
			Model m = it.next();
			if (m.getName().equals(newModel.getName())) {
				found++;
			}
		}
		if (found == 0) {
			getModels().add(newModel);
			return true;
		} else {
			Debugger.out("AddUniqueModel: the model '"+newModel.getName()+"' alread exists!");
		}
		return false;
	}

	public boolean addUniqueView (View newView) {
		Iterator<View> it = getViews().iterator();
		int found = 0;
		while (it.hasNext()) {
			View v = it.next();
			if (v.getName().equals(newView.getName())) {
				found++;
			}
		}
		if (found == 0) {
			getViews().add(newView);
			return true;
		} else {
			Debugger.out("AddUniqueView: the view '"+newView.getName()+"' alread exists!");
		}
		return false;
	}

	public ArrayList<View> getViews() {
		return views;
	}

	protected ArrayList<Model> getModels() {
		return models;
	}

	public void setMainViewName(String mainView) {
		this.mainViewName = mainView;
	}

	public String getMainViewName() {
		return mainViewName;
	}

	public FXSettings getTheFXSettings() {
		return theFXSettings;
	}

	public void setTheFXSettings(FXSettings theFXSettings) {
		this.theFXSettings = theFXSettings;
	}
}
