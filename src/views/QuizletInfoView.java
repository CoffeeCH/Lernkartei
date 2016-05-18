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

public class QuizletInfoView extends FXView
{
	public QuizletInfoView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		// Buttons
		AppButton backBtn = new AppButton("_Zur�ck");
		
		
		
		//Labels (f�r die Infotexte)
		Label labelTitel = new Label("Quizlet Info");
		Label labelText = new Label("Bla bla bla...");
		labelText.setWrapText(true);

		labelTitel.setId("impressumtext");
		labelText.setId("impressumtext");

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
			
		
		// TODO Auto-generated method stub
		return borderPane;
	}

	@Override
	public void refreshView ()
	{
	}
}
