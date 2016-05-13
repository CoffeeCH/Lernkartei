package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXView;


/**
 * Optionen
 * 
 * @author miro
 *
 */
public class OptionsView extends FXView
{
	public OptionsView (String newName, Controller newController)
	{
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer ()
	{
		Label cardLimitDescription = new Label("Anzahl Karten, die auf einmal gelernt werden, limitieren.");
		TextField cardLearnLimit = new TextField("");
		AppButton back = new AppButton("_Zur�ck");

		cardLimitDescription.setMaxWidth(200);
		cardLimitDescription.setWrapText(true);
		cardLearnLimit.setMaxWidth(200);
		
		VBox mainLayout = new VBox();
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(cardLimitDescription, cardLearnLimit, back);

		back.setOnAction(e -> getController().showMainView());

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
	}
}
