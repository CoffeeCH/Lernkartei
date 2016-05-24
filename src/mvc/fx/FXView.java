package mvc.fx;

import java.net.URL;

import debug.Logger;
import debug.Supervisor;
import globals.Environment;
import globals.Globals;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mvc.View;
/**
 * Abstract FX Toolkit dependent FX-View
 * =====================================
 * - extends the View features
 * 
 * @author  hugo-lucca
 * @version 2016
 */
public abstract class FXView extends View
{
	private Scene scene;
	private final BorderPane mainLayout = new BorderPane();
	private FXController myFXController;
	private boolean constructed = false;
	protected Parent myParentLayout;

	public void construct (String newName) {
		// call this to construct the view
		myParentLayout = constructContainer();
		if (myParentLayout==null) {
			myParentLayout = getMainLayout();
		}
		myParentLayout.setId(newName);
		setupScene(myParentLayout);
		this.constructed = true;
	}

	public FXView(FXController newController) {
		// this constructor is the same for all view's on same stage
		//super(newName);
		myFXController = newController;
		scene = null;
	}

	public FXController getFXController() {
		if (myFXController != null) return myFXController;
		Supervisor.warnAndDebug(this, "FXView("+myParentLayout.getId()+").getFXController(): no FXController defined for this stage!");
		return null;
	}
	
	public FXController getController() {
		
		return getFXController();
	}
	
	public Stage getWindow () {
		return this.getFXController().getMyFXStage().getStage();
	}
	
	protected void setVisible()
	{
		debug.Debugger.out("Get window("+myParentLayout.getId()+")...");
		Stage stage = getWindow();
		if (stage != null) {
			if (scene != null) {
				debug.Debugger.out("Set scene("+myParentLayout.getId()+")...");
				stage.setScene(scene);
			} else {
				Logger.log("FXView("+myParentLayout.getId()+").setVisible() has no scene!");
			}
			//Logger.log("stage show....");
			debug.Debugger.out("Set stage("+myParentLayout.getId()+")...");
			stage.show();
		} else {
			Logger.log("FXView("+myParentLayout.getId()+").setVisible() has no window!");
		}
		this.refreshView();
	}
	
	private boolean loadCSS (String stylePath) {
		URL url = this.getClass().getResource(stylePath);
		if (url != null) {
			String urlstr = url.toExternalForm();
			if (scene != null && urlstr != null) {
				scene.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
				return  true;
			} else {
				Logger.log("FXView("+myParentLayout.getId()+").setUpScene() no css-url found for "+stylePath);
			}
		} else {
		 //  Debugger.out("FXView("+getName()+").setUpScene(): no css ressource found for "+stylePath);
		}
		return false;
	}

	public void setupScene(Parent p) {
		Double width = getFXController().getMyFXStage().getOPTIMAL_WIDTH();
		Double height = getFXController().getMyFXStage().getOPTIMAL_HEIGHT();
		this.scene = new Scene(p, width, height);
		// TODO: add Color settings to scene
		
		String sep = Environment.getFileSep();
		
		String mainCSS = Globals.mainStyleFileName+Globals.CSSExtention;		
		if (!loadCSS(mainCSS)) {
			mainCSS = Globals.stylesSupPath+sep+Globals.mainStyleFileName+Globals.CSSExtention;		
			loadCSS(mainCSS);
		}
		String viewCSS = p.getId()+Globals.CSSExtention;		
		if (!loadCSS(viewCSS)){
			viewCSS = Globals.stylesSupPath+sep+p.getId()+Globals.CSSExtention;		
			loadCSS(viewCSS);
		}
	}

	public BorderPane getMainLayout() {
		return mainLayout;
	}

	public boolean isConstructed() {
		if (constructed == false) {
			Logger.log("FXView("+myParentLayout.getId()+").isContructed(): the View constructor must call the construct() method!");
		}
		return constructed;
	}
}
