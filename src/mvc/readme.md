=============================================================================================================

#	Model-View-Controller (MVC) - Konzept Applikatiosneutral                                #
	- nur Kernfunktionen f�r das MVC Zusammenspiel
	- GUI Toolkit neutral
	- f�r Applikationen mit relativ einfacher Navigation
	
>	@Author 	hugo-lucca
>	@Version	1.1 - 16.05.2016
=============================================================================================================

##	Inhalte:
>	- die **Interfaces** definieren die zwingend zu implementierenden Methoden (die Schnittstelle) f�r dieses MVC Konzept
	- die **abstrakten** Klassen sind "Halbfabrikate", d.h. vorbereitete Implementationen f�r eine einfache Beerbung
	
>	*Controller-Klassen (C)*:
		- ContollerInterface	Interface f�r alle **Controller** Klassen
		- Controller			Abstrakte Klasse f�r den Controller (C).
								Hat mindestens eine erbende Klasse, der "MainController" im app-package (controls).
		
>	*Model-Klassen (M)*:
		- ModelInterface	Interface f�r alle erbenden **Model** Klassen
		- DataModel			Abstrakte Klasse f�r ein komplettes Modell mit Datenstrukturen (D-M)
		- Model				Erweiterte Beispiel-Implementation vom DataModel und wird zum bsp. im FXViewModel
		
>	*View-Klassen (V)*:
		- ViewInterface		Interface f�r alle folgenden **View** Klassen
		- View				Abstrakte Klasse f�r ein View (V)
		- ViewModel			Ist nur eine Biespielimplementation, sollte nicht direkt beerbt werden, da die
							Toolkit Implementation Vorrang hat und multiple inheritance in Java nicht geht.

##	Abh�ngigkeiten ##
>	- globals package
	- debug package

##	How to use ##
>	Da es hier nur Halfabrikate (abstarkte Klassen) gibt, sollten zuerst Model, View, ViewModel und Controller GUI-Toolkit in einem separaten package spezifisch abgeleitet werden.
	
>	Die hier implementiert Grundfunktionalit�t beinhaltet:
	- der Controller h�lt und verwaltet die Liste der Model's und Views. Das erstellen dieser Komponenten und dem Starten
	  der Applikation soll in der abgeleiteten Klasse erledigt werden, hier wird lediglich die Reihenfolge festgelegt.
	  Dazu muss aber die Abgeleitete Klasse die methode start() bei ihrer Instazierung aufrufen!
	- der Controller bietet die Grundnavigation an. Damit sie funktioniert, muss in der abgeleiteten GUI-View bekannt 
	  sein, welcher GUI-Controller f�r die navigation zust�ndig ist, sonst kann man keine Methoden des Controllers aufrufen.
	- die abgeleiteten Views m�ssen zudem die Methode show() implementieren, damit zu einem View navigiert werden kann
	
>	Die refreshView() Methode vom View wird automatisch aufgerufen, wenn: 
	1. der sich Constructor des View's beim entsprechenden Model angemeldet hat, mit zum Bsp.:
			getController().getModel("model-name").registerView(this)
	2. die implementierte Model-Methode doAction(...) ruft intern die eigene refreshViews() Methode
	   nach Ver�nderung der eignen Daten auf.

>	Bemerkung: Die Anmeldung beim eigenen lokalen Model im ViewModel erfolgt automatisch und auch das refreshView();
	Es ist zudem kein Problem, wenn das View gleichzeitig bei mehreren Model's angemeldet ist.
	
>	Bei einem DataModel ist es zus�tzlich m�glich, einen direkten Datenaustausch zwischen Modell und View herzustellen. 
	Typischerweise wird in der View-Methode refreshView() den neuesten Datenstatus abgefragt und im eigenen View aktualisiert.

=========================================================================================================================
 

 
 