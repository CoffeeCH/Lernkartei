package views;

import java.util.ArrayList;

import debug.Debugger;
import globals.Functions;
import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;


public class LearnView extends FXViewModel
{
	public LearnView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	AppButton	successfulBtn	= new AppButton("_Richtig");
	AppButton	wrongBtn		= new AppButton("_Falsch");
	Label		headLbl			= new Label("");
	WebView		card			= new WebView();
	WebEngine	engine			= card.getEngine();

	boolean		frontIsShowed	= true;

	Button		preCard			= new Button("\u25C0");
	Button		nextCard		= new Button("\u25B6");

	int			counter			= 0;

	String[]	cardData		= new String[3];

	@Override
	public Parent constructContainer ()
	{
		AppButton backBtn = new AppButton("_Zur�ck");
		backBtn.setOnAction(e ->
		{
			counter = 0;
			getController().showView("stack");
		});

		headLbl.setId("bold");

		successfulBtn.setOnAction(e ->
		{
			int feedback = changeCardPriority("Richtig");
			if (feedback < 0)
			{
				counter--;
			}
			else if (feedback == 0)
			{
				Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ung�ltig");
			}
		});

		wrongBtn.setOnAction(e ->
		{
			int feedback = changeCardPriority("Falsch");
			if (feedback < 0)
			{
				counter--;
			}
			else if (feedback == 0)
			{
				Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ung�ltig");
			}
		});

		card.setMaxSize(320, 180);
		card.setId("bold");
		card.setDisable(true);
		
		preCard.setOnAction(e ->
		{
			counter = counter > 0 ? counter - 1 : counter;
			refreshView();
		});

		nextCard.setOnAction(e ->
		{
			counter++;
			refreshView();
		});
		
		VBox cardLayout = new VBox(20);
		cardLayout.setAlignment(Pos.CENTER);
		cardLayout.getChildren().addAll(card);
		
		cardLayout.setOnMouseClicked(e -> {
			turnCard();
		});
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, preCard, successfulBtn, wrongBtn, nextCard);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(cardLayout);
		mainLayout.setBottom(controlLayout);

		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLbl);

		mainLayout.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.T)
			{
				turnCard();
			}
			else if (e.getCode() == KeyCode.R)
			{
				int feedback = changeCardPriority("Richtig");
				if (feedback < 0)
				{
					counter--;
				}
				else if (feedback == 0)
				{
					Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ung�ltig");
				}
			}
			else if (e.getCode() == KeyCode.F)
			{
				int feedback = changeCardPriority("Falsch");
				if (feedback < 0)
				{
					counter--;
				}
				else if (feedback == 0)
				{
					Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ung�ltig");
				}
			}
		});
		
		getController().getModel("learn").registerView(this);
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		if (getData() == null || getData().equals(""))
		{
			successfulBtn.setDisable(false);
			wrongBtn.setDisable(false);
		}
		else
		{
			headLbl.setText(getData());
			ArrayList<String> cards = getController().getModel("learn").getDataList(getData());
			if (counter < cards.size())
			{
				successfulBtn.setDisable(false);
				wrongBtn.setDisable(false);
				nextCard.setDisable(false);
				String d = cards.get(counter); // Ensure valid counter variable
				cardData = d.split(Globals.SEPARATOR);
				
				for (int i = 1; i < 3; i++)
				{
					cardData[i] = Functions.simpleBbCode2HTML(cardData[i], Globals.evenTags);
					cardData[i] = Functions.realBbCode2HTML(cardData[i], Globals.pairedTags);
				}
				
				engine.loadContent(cardData[1]);
				frontIsShowed = true;
			}
			else
			{
				successfulBtn.setDisable(true);
				wrongBtn.setDisable(true);
				nextCard.setDisable(true);
				engine.loadContent("");
				cardData = null;
				counter = cards.size();
			}
		}
	}

	public void clearShuffle ()
	{
		getController().getModel("learn").getDataList(null).clear();
		getController().getModel("learn").setString(null);
	}
	
	private void turnCard()
	{
		engine.loadContent(frontIsShowed ? cardData[2] : cardData[1]);
		frontIsShowed = !frontIsShowed;
	}
	
	private int changeCardPriority(String command)
	{
		counter++;
		return getController().getModel("learn").doAction(command, cardData[0]);
	}
}