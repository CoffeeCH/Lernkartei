=============================================================================================================

#	Model-View-Controller (MVC) Konzept f�r diverse Applikationen                           #
	(GUI Toolkit neutral)
	
	@Author 	hugo-lucca
	@Version	1.1 - 16.05.2016
=============================================================================================================

##	Inhalte:
>	- die Interfaces definieren die zwingend zu implementierenden Methoden (die Schnittstelle)
>	- die abstrakten Klassen sind "Halbfabrikate", d.h. vorbereitete Implementationen f�r eine einfache Beerbung, so
>	- dass nur noch der f�r die Applikation notwendige Code bei M, V und C implementiert werden muss 
	
>	- Controller-Klassen (C):
		- ContollerInterface	Interface f�r alle Controller Klassen
		- Controller			Abstrakte Klasse f�r den Controller (C).
								Hat evtl. nur eine erbende Klasse, der MainController in der Applikation.
		
>	- Model-Klassen (M):
		- ModelInterface	Interface f�r alle folgenden Model Klassen
		- DataModel			Abstrakte Klasse f�r ein komplettes Modell mit Datenstrukturen (D-M)
		- Model				Erweiterte Beispiel-Implementation vom DataModel und dient der Anwendung in ViewModel
		
>	- View-Klassen (V):
		- ViewInterface		Interface f�r alle folgenden View Klassen
		- View				Abstrakte Klasse f�r ein View (V)
							Ein neutrales ViewModel lohn sich nicht, denn die Implementation ist trivial, s.u.

##	Abh�ngigkeiten:
>	- debug-Package
>	- mvc.test-Package beinhaltet die Komponententests
>	- die hiervon abgeleiteten C, M und V werden zu besseren �bersicht in separate packages gehalten 

##	How to use:
>	Da es hier praktisch nur um Halfabrikate geht (abstarkte Klassen),sollten grunds�tzlich zuerst 3 weitere Klassen f�r  
	View, ViewModel und Controller GUI-Toolkit spezifisch abgeleitet werden.
	Erst in diesem separaten package wird erkl�rt, wie das Ganze dann anzuwenden ist.
	
>	Die hier bereits implementierte Grunfunktionalit�t betrifft das Zusammenspiel 
	zwischen den 3 Basisklassen Model, View und Controller.
	
>	Die hier zur verf�gte Grundfunktionalit�t ist:
	- der Controller h�lt und verwaltet die Liste der Model's und Views. Das erstellen dieser Komponenten und dem Starten
	  der Applikation soll in der abgeleiteten Klasse erledigt werden, hier wird lediglich die Reihenfolge festgelegt.
	  Dazu muss aber die Abgeleitete Klasse die methode start() bei ihrer Instazierung aufrufen!
	- der Controller bietet die Grundnavigation an. Damit sie funktioniert, muss in der abgeleiteten GUI-View bekannt 
	  sein, welcher GUI-Controller f�r die navigation zust�ndig ist, sonst kann man keine Methoden des Controllers aufrufen.
	- die abgeleiteten Views m�ssen zudem die Methode show() implementieren, damit zu einem View navigiert werden kann
	- die Model Klasse muss nicht abgeleitet werden und kann auch direkt verwendet werden.
	
>	Die refreshView() Methode vom View wird automatisch aufgerufen, wenn: 
	1. der Constructor des View's meldet sich beim entsprechenden Model an mit zum Bsp.:
			getController().getModel("model-name").registerView(this)
	2. die implementierte Model-Methode doAction(...) ruft intern die eigene refreshViews() Methode
	   nach Ver�nderung der eignen Daten auf.

>	Bemerkung: Die Anmeldung beim eigenen lokalen Model im ViewModel erfolgt automatisch und auch das refreshView();
	Es ist zudem kein Problem, wenn das View gleichzeitig bei mehreren Model's angemeldet ist.
	
>	Bei einem DataModel ist es zus�tzlich M�glich einen direkten Datenaustausch zwischen Modell und View herzustellen. 
	Typischerweise wird in der View-Methode refreshView() den neuesten Datenstatus abgefragt und im eigenen View aktualisiert.
	Daf�r ist es meist notwendig gewisse Daten-Anzeigeelemente als Attribut zu halten und zu verwalten.

=========================================================================================================================
 

 
 