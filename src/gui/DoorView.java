package gui;

import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DoorView
{
	Stage	window;
	Scene	scene;

	public DoorView (Stage primaryStage, MainController controller)
	{
		window = primaryStage;
		BorderPane borderPane;
		Button zurueckButton;
		Button neueTuer;
		Button loescheTuer;
		Button weitereTueren;
		
		
		//Buttons
		zurueckButton = new Button("zur�ck");
		neueTuer = new Button("Neue T�r");
		loescheTuer = new Button("L�sche T�r");
		weitereTueren = new Button("weitere T�ren");
		
		// Box und Pane erstellen
		HBox hBox = new HBox(20);
		borderPane = new BorderPane();
		hBox.setAlignment(Pos.CENTER);
		//Alle Buttons in die HBox
		hBox.getChildren().addAll(zurueckButton, neueTuer, loescheTuer, weitereTueren);
		
		//Die HBox in die Bottom BorderPane
		borderPane.setBottom(hBox);
		
		
		zurueckButton.setMinWidth(150);
		neueTuer.setMinWidth(150);
		loescheTuer.setMinWidth(150);
		weitereTueren.setMinWidth(150);
		
		borderPane.setPadding(new Insets(15));
		zurueckButton.setOnAction(e -> controller.showMain());
		
		scene = new Scene(borderPane, 800, 450);
	}

	public void show ()
	{
		window.setScene(scene);
	}
}


