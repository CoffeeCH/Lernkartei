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
		super(setName, primaryStage, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zur�ck");
		AppButton neueTuer = new AppButton("Neue T�r");
		AppButton loescheTuer = new AppButton("L�sche T�r");
		AppButton weitereTueren = new AppButton("weitere T�ren");

		// Layout f�r Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		
		hBox.getChildren().addAll(zurueckButton, neueTuer, loescheTuer, weitereTueren);

		
		// Laayout f�r die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		
		borderPane.setBottom(hBox);
		
		// Behaviour
		zurueckButton.setOnAction(e -> getController().showMain());
		neueTuer.setOnAction(e -> Alert.simpleInfoBox("Info", "Noch nicht implementiert"));
		loescheTuer.setOnAction(e -> Alert.simpleInfoBox("Info", "Noch nicht implementiert"));
		weitereTueren.setDisable(true);

		this.setScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		refresh();
		// TODO Auto-generated method stub
	}

}
