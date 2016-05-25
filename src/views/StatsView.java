package views;

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
import views.components.AppButton;

/**
 * Diese Klasse soll die gleiche Funktionalit�t wie StatisticsView haben und
 * diese dann auch ersetzen Sie soll beliebig viele S�ulen generieren
 * 
 * @author Joel H�berli
 *
 */

public class StatsView extends FXView
{
	public StatsView(String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	private StatisticsModel statisticsModel;


	final BorderPane pane = new BorderPane();
	final AppButton back = new AppButton("_Zur�ck");
	// Achsen erstellen
	final CategoryAxis xAchse = new CategoryAxis();
	final NumberAxis yAchse = new NumberAxis();

	final HBox diagram = new HBox(50);
	final HBox controls = new HBox(50);
	final HBox rankings = new HBox(50);
	
	
	// private int countLoadsOfRefreshView = 0;

	@Override
	public Parent constructContainer()
	{

		// HBox f�r die Buttons / Controls
		controls.setAlignment(Pos.BOTTOM_CENTER);
		controls.setPadding(new Insets(15));

		// Buttons / Controls
		back.setOnAction(e -> getController().showMainView());
		controls.getChildren().addAll(back);

		pane.setBottom(controls);
		
		// HBox f�r die Rankings
		rankings.setAlignment(Pos.CENTER_LEFT);
		rankings.setPadding(new Insets(15));

		// HBox f�r Diagramm
		diagram.setAlignment(Pos.CENTER);
		
		return pane;
	}

	// Hier werden die Daten f�r das Diagramm geholt. Ich habe daf�r im
	// StatisticsModel eigene Funktionen erstellt, da ich nicht mit ArrayLists
	// arbeiten kann.
	// Ich Caste darum auch auf das StatisticsModel und greife nicht herk�mmlich
	// nur mit getController() darauf zu.
	@Override
	public void refreshView()
	{

		diagram.getChildren().clear();
		rankings.getChildren().clear();
		
		// BarChart erstellen
		BarChart<String, Number> bc = new BarChart<String, Number>(xAchse, yAchse);
		bc.setAnimated(true);

		// Daten f�r Rangliste abholen �ber StatisticsModel und dann
		// Rangliste.java
		statisticsModel = ((StatisticsModel) getController().getFXModel("statistics"));

		xAchse.setLabel("Karteien");
		yAchse.setLabel("Ergebnis (%)");
		try
		{
			// ListView
			ListView<String> Ranks = new ListView<String>();

			if (statisticsModel.getObservableDiagrammList("saulendiagramm") == null && statisticsModel.getObservableDataList("Rangliste") == null)
			{
				// Dieses TextField wird geaddet, wenn keine Daten f�r die
				// Rangliste oder die Rangliste gefunden werden k�nnen.
				TextField m = new TextField();
				m.setText("Es tut uns leid aber wir konnten keine Daten zur Auswertung Ihrer Statistik finden");
				diagram.getChildren().add(m);
			} else
			{
				statisticsModel.doAction("DeleteOldData");
				if (statisticsModel != null)
				{
					Ranks.setItems(statisticsModel.getObservableDataList("Rangliste"));
					
					// Daten f�r das Diagramm. Die verarbeitugn und bereitstellung
					// findet alles in Diagramm.java (getChartData()) statt.
					bc.getData().addAll(statisticsModel.getObservableDiagrammList("saulendiagramm"));
				} else
				{
					System.out.println("Kein sm model!!!");
				}
				rankings.getChildren().addAll(Ranks);

				// Der HBox "Diagramm" das BarChart adden welches das das
				// S�ulendiagramm beinhaltet.
				// Wird hier gemacht, weil bei Fehler andere Komponente geaddet
				// wird (siehe weiter oben das TextField)
				diagram.getChildren().addAll(bc);

				// Hier werden s�mtliche Daten auch in den anderen Klassen �ber
				// das Model gel�scht, damit sicher ist, dass nirgends
				// Datenleichen herumgeistern
				statisticsModel.doAction("DeleteOldData");				
			}
		} catch (Exception e)
		{
			Debugger.out("StatsView Exception: ");
			e.printStackTrace();
		}

		pane.setCenter(diagram);
		pane.setLeft(rankings);
	}
}
