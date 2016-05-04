package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXView;

public class ImpressumView extends FXView
{
	/*
	 * Ich nehme mal an wir werden die Texte mit Labels anzeigen.
	 * Ich hab versucht den Hintergrund etwas heller zu machen nur einen kleinen Akzent.
	 * Ich habs nicht und versuche es bald mit css. Es ist aber noch nicht gekl�rt ob das 
	 * wirklich Labels sein sollten oder ob es was besseres gibt.
	 * 
	 *    -------
	 *   � To Do �
	 * 	  -------
	 * - Der Text sollte besser formatiert sein. (Andere Schrift)
	 * 
	 * - Der Hintergrund sollte ganz schwach heller sein,
	 *	 das Problem ist dass man bei Optionen vielleicht eine andere Farbe
	 *	 ausw�hlen kann als Hintergrund und wenn ich jetzt eine feste Farbe setzte
	 *   sieht es dann schr�g aus. Ich kann es ja nicht auf halb durchsichtig stellen.
	 *   
	 * - Das Formatieren k�nnte auch noch ein Problem sein. 
	 * 	 Mit \n kann man eine neue Zeile machen aber ich kenn die anderen nicht.
	 * 
	 */
	
	public ImpressumView (String setName, Controller controller)
	{
		super(setName, controller);
		
		// Buttons
		AppButton backBtn = new AppButton("Zur�ck");
		
		//Labels (f�r die Infotexte)
		Label labelTitel = new Label("Impressum");
		Label labelText = new Label("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n \n Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. \n \n Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		labelText.setWrapText(true);

		labelTitel.setId("impressumtext");
		labelText.setId("impressumtext");

		Double size = getWindow().getWidth()*.95;
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
		backBtn.setOnAction(e -> getController().getView("help").show());
		
		// Layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		borderPane.setBottom(naviBox);
		borderPane.setCenter(BoxMitText);
		borderPane.setTop(TitelBox);
		
		setupScene(new Scene(borderPane, getController().getFXSettings().OPTIMAL_WIDTH, getController().getFXSettings().OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		return;
	}
}
