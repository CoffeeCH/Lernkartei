package gui;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Gamestartfenster
 * 
 * @author miro-albrecht & hugo-lucca
 *
 */
public class DoorView extends View
{
	public DoorView (String setName, Stage primaryStage, MainController controller)
	{
		super (setName, primaryStage);
		this.setMyController(controller);

		//Buttons
		AppButton zurueckButton = new AppButton("zur�ck");
		AppButton neueTuer 		= new AppButton("Neue T�r");
		AppButton loescheTuer 	= new AppButton("L�sche T�r");
		AppButton weitereTueren = new AppButton("weitere T�ren");

		// Box und Pane erstellen
		HBox hBox = new HBox(20);
		BorderPane borderPane = new BorderPane();
		hBox.setAlignment(Pos.CENTER);
		//Alle Buttons in die HBox
		hBox.getChildren().addAll(zurueckButton, neueTuer, loescheTuer, weitereTueren);
		
		//Die HBox in die Bottom BorderPane
		borderPane.setBottom(hBox);
		
		borderPane.setPadding(new Insets(15));
		zurueckButton.setOnAction(e -> controller.showMain());
		
		this.setScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

}


