package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mvc.Controller;
import mvc.FXView;
/**
 * Gamestartfenster
 * ================
 * Zweck dieses BS ist eine Art Warte- und Info-BS (zum Bsp. Anzahl Punkte) zum Gamestart, 
 * mann kann dann alternativ weiter lernen.
 * 
 * @author hugo-lucca
 *
 */
public class GameView extends FXView
{
	public GameView(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	Text text;

	@Override
	public Parent constructContainer() {
		text = new Text("Starting game...");
		text.setId("fancytext"); // CSS formatierte Meldung auf BS bringen (mit div. Schrifteffekten)

		// Button f�r Zur�ck zum Hauptmenue:
		AppButton btn = new AppButton("_Zur�ck...");
		btn.setOnAction(e -> getController().showMainView());

		// Erstellt VBox Layout f�r beide obige Elemente:
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(text, btn);
		
		// VBox in neuem Borderpane einf�gen, zwingend wenn Hintergrund neu sein soll
		// CSS liefert neue Darstellung:
		BorderPane bp = new BorderPane();
		bp.setCenter(tempVBox);
		bp.setId("gamebg");
		return bp;
	}

	@Override
	public void refreshView() {
		text.setText("Starting game (please wait)...");
		getController().getModel("game").doAction("start");
	}
}
