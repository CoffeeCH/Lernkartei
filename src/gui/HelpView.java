package gui;

import application.Constants;
import application.HelpController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mvc.Controller;
import mvc.FXView;

/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends FXView
{
	BorderPane mainLayout  = new BorderPane();
	AppButton impressumBtn = new AppButton("Impressum");
	AppButton anleitungBtn = new AppButton("Anleitung");
	AppButton indexBtn     = new AppButton("Index");
	String subTitle = "Hilfe";
	
	public HelpView (String setName, Controller controller)
	{
		super (setName, controller);
		init();
	}

	public HelpView (String setName)
	{
		super (setName, new HelpController(new Stage()));
		init();
	}

	private void init () {
		this.getWindow().setTitle(Constants.appTitle+subTitle+Constants.appVersion);
		this.getWindow().setResizable(false);

		impressumBtn.setOnAction(e -> getController().getView("impressum").show());
		anleitungBtn.setOnAction(e -> getController().getView("manual").show());
		indexBtn.setOnAction(e -> getController().getView("index").show());

		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(impressumBtn, anleitungBtn, indexBtn);

		tempVBox.setId("help");
		this.setupScene(new Scene(tempVBox, getController().getFXSettings().OPTIMAL_WIDTH, 150+getController().getFXSettings().OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
	
}
