================================================================

#	All Application's View's: the GUI (with JavaFX)   #

================================================================

##	Inhalt
<	- alle ben�tigten Views und GUI-Teilkomponenten
	- jeder eigener View mit Inhalte und Darstellungen
	- das Verhalten der einzelnen Kn�pfe wird hier bestimmt
	- falls n�tig werden Modellklassen aufgerufen oder Model's lokal in View's eingebaut
	
##	Abh�ngigkeiten
>	- MainController (zum Navigieren)
	- Weitere Controller- und Model-Klassen um die Funktionalit�t zu gew�hrleisten
	- debug
	- die style.css Datei (bestimmt die Darstellung)
	- div. Darstellungsinhalte (Bilder etc., k�nnen auch ausgelagert werden)

=======================================================================================

##	HowTo
>	- jede Klasse erbt von FXView oder FXVeiwModel (=mit eigenem, lokalem Daten-Modell)
	- der Konstruktor dieser Klassen wird programmiert, dabei sind folgende Punkte zu beachten:
		- die erste Zeile und der Konstruktor (super(...)) ist f�r alle gleich zu implementieren
		- am Ende sollte die Scene erstellt werden
		- beim zuerst anzuzeigenden View muss am Schluss noch this.show() aufgerufen werden
		- das View kann in seinen Ereignis-Behandlungen mit den doAction()-Methoden eines Model-Objekts
		  Befehle erteilen (R�ckmeldung hier nur, ob erfolgreich oder nicht) 
		- die entsprechenden Modelle k�nnen �ber den Hauptkontroller beim Namen abgerufen werden
	- ein FXViewModel erm�glicht es, dem View ein eigenes Modell (ein String oder eine String-Liste) 
	  direkt und lokal zu verwalten (Zwischenspeicher f�r Tempor�re Inhalte)
	- arbeitet das View mit einem nicht lokalen Model (Applikationsdaten), dann muss noch folgendes beachtet werden:
		- das nicht lokale Applikationsmodel muss ein DataModel sein
		- des View muss sich im Konstruktor beim entsprechenden Model anmelden mit:
			(getController().getModel("ModellName").registerView(this))
		- ein View kann sich bei mehreren Modellen anmelden, ein Modell kann mehrere Anmeldungen haben
		- die Methode refreshViews() muss im applikations-Model nach jeder �nderung der angezeigten Daten aufrufen
		- die Methode refreshView() vom View muss dann wie folgt angepasst werden:
			- das Modell nach den ver�nderten Daten abfragen
			- die Daten in den entsprechenden Anzeigeelemente (als Attribut im View) aktualisieren
			- es sollte keine weiteren refresh-Aufrufe notwendig sein (FX macht den Rest)
			
=============================================================================================================
 
