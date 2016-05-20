package views;

import java.util.ArrayList;

import debug.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.StatisticsModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
/**
 * Diese Klasse soll die gleiche Funktionalit�t wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele S�ulen generieren
 * 
 * @author Joel H�berli
 *
 */

public class StatsView extends FXView
{	
	public StatsView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}
	
	HBox Diagram = new HBox(50);
	HBox Controls = new HBox(50);
	HBox Rankings = new HBox(50);
	AppButton back = new AppButton("_Zur�ck");
	BorderPane Pane = new BorderPane();
	//Achsen erstellen
	CategoryAxis xAchse = new CategoryAxis();
	NumberAxis yAchse = new NumberAxis();
	//BarChart erstellen
	BarChart<String, Number> BC = new BarChart<String, Number>(xAchse, yAchse);
	//ListView
	ListView<String> Ranks = new ListView<String>();
			
	ArrayList<String> Karteien = new ArrayList<String>();
	ArrayList<Double> Punkte = new ArrayList<Double>();
			
	@Override
	public Parent constructContainer() {
		
		BC.setAnimated(true);
		
		//HBox f�r Diagramm
		Diagram.setAlignment(Pos.CENTER);
		
		//HBox f�r die Buttons / Controls
		Controls.setAlignment(Pos.BOTTOM_CENTER);
		Controls.setPadding(new Insets(15));
		
		//HBox f�r die Rankings
		Rankings.setAlignment(Pos.CENTER_LEFT);
		Rankings.setPadding(new Insets(15));
		
		//Buttons / Controls
		back.setOnAction(e -> {getController().showMainView();delOldStats();});
		Controls.getChildren().addAll(back);
		
		Pane.setCenter(Diagram);
		Pane.setBottom(Controls);
		Pane.setLeft(Rankings);
		
		return Pane;
	}

	//Hier werden die Daten f�r das Diagramm geholt. Ich habe daf�r im StatisticsModel eigene Funktionen erstellt, da ich nicht mit ArrayLists arbeiten kann. 
	@Override
	public void refreshView()
	{	
		
		xAchse.setLabel("Karteien");
		yAchse.setLabel("Ergebnis (%)");
		try {
			
			if (((StatisticsModel) getController().getFXModel("statistics")).getObservableDiagrammList("saulendiagramm") == null && ((StatisticsModel) getController().getFXModel("statistics")).getObservableDataList("Rangliste") == null) {
				TextField m = new TextField();
				m.setText("Es tut uns leid aber wir konnten keine Daten zur Auswertung Ihrer Statistik finden");
				Diagram.getChildren().add(m);
			} else {
				Ranks.setItems(((StatisticsModel) getController().getFXModel("statistics")).getObservableDataList("Rangliste"));
				Rankings.getChildren().addAll(Ranks);
				
				BC.getData().addAll(((StatisticsModel) getController().getFXModel("statistics")).getObservableDiagrammList("saulendiagramm"));
				
				Diagram.getChildren().addAll(BC);
				
				((StatisticsModel) getController().getFXModel("statistics")).doAction("DeleteOldData");
			
				System.out.println("Deleted old Data");
			}
		} catch (Exception e) {
			Debugger.out("StatsView Exception: " + e.fillInStackTrace());
		}
	}
	
	private void delOldStats()
	{
		Karteien.clear();
		Punkte.clear();
		BC = null;
		Ranks = null;
	}

}
