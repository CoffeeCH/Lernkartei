package gui;

import java.util.ArrayList;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Zeigt alle T�ren an. Erlaubt die Erstellung und das L�schen von T�ren.
 * 
 * @author miro albrecht & hugo lucca
 *
 */
public class DoorView extends View
{
	// Zeigt T�ren dynamisch an
	VBox doorLayout;

	public DoorView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Initialisiere Layout f�r T�ren
		doorLayout = new VBox(20);
		doorLayout.setAlignment(Pos.CENTER);

		// Buttons
		AppButton backBtn = new AppButton("Zur�ck");
		AppButton newDoorBtn = new AppButton("Neue T�r");

		// Trash Image
		Image trashImg = new Image("gui/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout f�r Controls (Hauptsteuerung)
		HBox controlsLayout = new HBox(20);
		controlsLayout.setAlignment(Pos.CENTER);
		controlsLayout.getChildren().addAll(backBtn, newDoorBtn, trashImgView);

		// Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));

		mainLayout.setCenter(doorLayout);
		mainLayout.setBottom(controlsLayout);

		
		// Behaviour
		backBtn.setOnAction(e -> getController().showMain());

		newDoorBtn.setOnAction(e ->
		{
			String doorName = Alert.simpleString("Neue T�r", "Wie soll die neue T�r heissen?");
			if (doorName != null && !doorName.equals(""))
			{
				getController().getMyModel("door").doAction("new", doorName);
			}
		});

		trashImgView.setOnDragOver(e ->
		{
			if (e.getGestureSource() != trashImgView &&
					e.getDragboard().hasString())
			{
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			e.consume();
		});
		
		trashImgView.setOnDragDropped(event ->
		{
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString())
			{
				if (Alert.ok("Achtung", "Willst du die T�r '" + db.getString() + "' wirklich l�schen?"))
				{
					getController().getMyModel("door").doAction("delete", db.getString());
				}
				success = true;
			}
			
			event.setDropCompleted(success);
			event.consume();
		});

		this.setupScene(new Scene(mainLayout, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
		getController().getMyModel("door").registerView(this);
	}

	@Override
	public void refreshView ()
	{
		doorLayout.getChildren().clear();
		
		ArrayList<String> doorNames = getController().getMyModel("door").getData("doors");
		ArrayList<AppButton> doors = new ArrayList<>();
		
		if (doorNames != null)
		{
			for (String s : doorNames)
			{
				doors.add(new AppButton(s));
			}
		}

		for (AppButton a : doors)
		{
			a.setOnAction(e ->
			{
				View v = getController().show("boxview");
				v.setData(a.getText());
			});
			
			a.setOnDragDetected(e ->
			{
				Dragboard db = a.startDragAndDrop(TransferMode.MOVE);

				ClipboardContent content = new ClipboardContent();
				content.putString(a.getText());
				db.setContent(content);

				e.consume();
			});
			a.setOnDragDone(event ->
			{
				if (event.getTransferMode() == TransferMode.MOVE)
				{
					doors.remove(a);
				}
				event.consume();
			});
		}

		doorLayout.getChildren().addAll(doors);
	}
}
