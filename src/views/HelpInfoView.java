package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;

/**
 *	Hilfesystem Info Anzeige
 * 
 * @author hugo-lucca
 *
 */
public class HelpInfoView extends FXView
{

	public HelpInfoView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub
		
				// Buttons
				AppButton backBtn = new AppButton("_Zur�ck");
					
				//Labels (f�r die Infotexte)
				Label labelTitel = new Label("Anleitung");
				
				//Hier kommt der angezeigte Text hin:
				Label labelText = new Label("Erste Schritte:\n\nWenn du das Programm gestartet hast siehst du das Men�. Dr�cke den obersten Button Lernen. Dannach bist du bei den T�ren. Das sind die Oberthemen. Unten siest du einen Button Erstelle neue T�r. Klicke auf ihn und dann kannst du der T�r einen Namen geben. Zum Biespiel Mathe.\n Klicke nun auf die neue T�r die du erstellt hast und du landest nun bei den Boxen. Das sind die Unterthemen wie zum Beispiel Algebra und Geometrie in unserem Fall. Klcike auf Erstelle neue Box und ein kleines Fenster mit einer Auswahl erscheint. Du kannst selber K�rtchen erstellen oder sie von Quizlet importieren. Du entschiedest.\n\nWenn du fertig bist klicke auf die Box die du erstellt hast und nun kannst du K�rtchen hinzuf�gen wenn du auf Bearbeiten klickst. Hast du dies getan klicke auf Lernen und das erste K�rtchen erscheint. Dr�cke auf den drehen Button um die R�ckseite anzeigen zu lassen. Anschliessen klicke auf Richtig oder Falsch um dem Programm zu sagen ob du das K�rtchen wusstest oder nicht.\n\nWichtiges:\nKontakte:\netc...");
				
				//Zeilenumbruch am Fensterrand
				labelText.setWrapText(true);

				//IDs f�r CSS
				labelTitel.setId("impressumtext");
				labelText.setId("impressumtext");

				//Damit der Text nicht bis zum Fensterrand geht sondern noch etwas abstand hat
				Double size = getController().getMyFXStage().getOPTIMAL_WIDTH()*.95;
				size += 10;
				labelText.setPrefWidth(size);
				labelTitel.setPrefWidth(size);
				
				//Box f�r die Navigation
				HBox naviBox = new HBox(10);
				naviBox.getChildren().addAll(backBtn);
					
				//Box f�r Titel
				VBox TitelBox = new VBox(10);
				TitelBox.getChildren().addAll(labelTitel);
				TitelBox.setAlignment(Pos.CENTER);
						
				//Box f�r Mitte Text
				VBox BoxMitText = new VBox(20);
				BoxMitText.getChildren().addAll(labelText);
				BoxMitText.setAlignment(Pos.CENTER);
				
				// Behaviour
				backBtn.setOnAction(e -> getController().showMainView());
				
				// Layout
				BorderPane borderPane = new BorderPane();
				borderPane.setPadding(new Insets(15));
				borderPane.setBottom(naviBox);
				borderPane.setCenter(BoxMitText);
				borderPane.setTop(TitelBox);
				
		return borderPane;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
