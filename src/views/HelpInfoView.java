package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

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
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub
		
		Label labelTitel = new Label("Anleitung");
		Label labelText = new Label("Erste Schritte:\n\nWenn du das Programm gestartet hast siehst du das Men�. Dr�cke den obersten Button Lernen. Dannach bist du bei den T�ren. Das sind die Oberthemen. Unten siest du einen Button Erstelle neue T�r. Klicke auf ihn und dann kannst du der T�r einen Namen geben. Zum Biespiel Mathe.\n Klicke nun auf die neue T�r die du erstellt hast und du landest nun bei den Boxen. Das sind die Unterthemen wie zum Beispiel Algebra und Geometrie in unserem Fall. Klicke auf Neuer Stapel und ein kleines Fenster mit einer Auswahl erscheint. Du kannst selber K�rtchen erstellen oder sie von Quizlet importieren. Du entschiedest.\n\nWenn du fertig bist klicke auf die Box die du erstellt hast und nun kannst du K�rtchen hinzuf�gen wenn du auf Bearbeiten klickst. Hast du dies getan klicke auf Lernen und das erste K�rtchen erscheint. Dr�cke auf den drehen Button um die R�ckseite anzeigen zu lassen. Anschliessen klicke auf Richtig oder Falsch um dem Programm zu sagen ob du das K�rtchen wusstest oder nicht.\n\nK�rtchen bearbeiten/hinzuf�gen:\n\nUm ein K�rtchen zu erstellen, bearbeiten oder hunzuzuf�gen gehe bei den Stapeln(Karteik�stchen) auf bearbeiten. Nun hast du eine Liste der K�rtchen sofern du schon welche erstellt hast. Nun kannst du die Frage und die Antwort eintippen in die Felder. Klicke dann auf das H�kchen am Ende der Zeile um zu best�tigen. Wenn du auf den Stift neben dem H�ckchen klickst kannst du bereits erstellte K�rtchen bearbeiten.\netc...");
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);

		labelTitel.setId("impressumtitel");
		labelText.setId("impressumtext");
			
		AppButton backBtn = new AppButton("_Zur�ck");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);
		
				
		scroller.setContent(labelText);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);  
			
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_CENTER);
		controlLayout.getChildren().addAll(backBtn);
		controlLayout.setPadding(new Insets (10));

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(scroller);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
