package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.Alert;
import views.components.AppButton;
import views.components.HomeButton;


/**
 * Zeigt alle T�ren an. Erlaubt die Erstellung und das L�schen von T�ren.
 * 
 * @author miro albrecht & hugo lucca
 *
 */
public class DoorView extends FXView
{
	public DoorView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	// Zeigt T�ren dynamisch an (muss im construct und im refresh verf�gbar sein)
	VBox doorLayout;

	@Override
	public Parent constructContainer ()
	{
		// Initialisiere Layout f�r T�ren
		doorLayout = new VBox(20);
		doorLayout.setAlignment(Pos.CENTER);
		ScrollPane scDoor = new ScrollPane(doorLayout);
		scDoor.setHbarPolicy(ScrollBarPolicy.NEVER);

		// Buttons
		HomeButton homeBtn = new HomeButton(getController());
		AppButton newDoorBtn = new AppButton("_Neue T�r");
		AppButton renameBtn = new AppButton("Umbennen");

		// Trash Image
		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout f�r Controls (Hauptsteuerung)
		HBox controlsLayout = new HBox(20);
		controlsLayout.setAlignment(Pos.CENTER); // NICHT MEHR ENTFERNEN
		controlsLayout.getChildren().addAll(homeBtn, newDoorBtn, renameBtn, trashImgView);

		// Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));

		mainLayout.setCenter(scDoor);
		mainLayout.setBottom(controlsLayout);

		newDoorBtn.setOnAction(e ->
		{
			String doorName = Alert.simpleString("Neue T�r", "Wie soll die neue T�r heissen?");
			if (doorName != null && !doorName.equals(""))
			{
				int succesful = getController().getModel("door").doAction("new", doorName);
				if (succesful == -1)
				{
					Alert.simpleInfoBox("T�r wurde nicht erstellt", "Dieser Name ist schon vergeben.");
				}
			}
		});

		renameBtn.setOnAction(e ->
		{
			getFXController().setViewData("rename", "door" + Globals.SEPARATOR + "doors");
			getFXController().showView("rename");
		});

		trashImgView.setOnDragOver(e ->
		{
			if (e.getGestureSource() != trashImgView && e.getDragboard().hasString())
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
					getController().getModel("door").doAction("delete", db.getString());
				}
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();
		});

		getController().getModel("door").registerView(this);
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		doorLayout.getChildren().clear();

		ArrayList<String> doorNames = getController().getModel("door").getDataList("doors");
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
			a.setId("DoorButtons");
			a.setOnAction(e ->
			{
				getController().setViewData("stack", a.getText());
				getController().showView("stack");
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
