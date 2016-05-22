package views.components;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

/**
 * Gamestartfenster
 * 
 * @author hugo-lucca
 *
 */
public class AppButton extends Button {
	
	final static int DEFAULT_BUTTON_WIDTH = 200;
	final static int DEFAULT_BUTTON_MIN_WIDTH = 150;
	/**
	 * Wir leiten eine Zwischenklasse ab, damit alle Buttons gleich initialisert werden k�nnen
	 * 
	 * Die Idee dahinter:
	 * -----------------
	 * - varaible Werte wie Gr�sse, die evtl. sp�ter noch zu berechnen sind, hier in FX l�sen, 
	 * 	 da dies ja in CSSn (ohne Web-Kit) nicht geht (keine Variablen)
	 * - den Rest der Darstellung in der CSS vornehmen, dort ist viel einfacher
	 * 
	 * @autor hugo-lucca
	 */
	public AppButton (String value) {
		super(value);
		setMinWidth(DEFAULT_BUTTON_MIN_WIDTH);
		setMaxWidth(DEFAULT_BUTTON_WIDTH);
		// alle Buttons sollen gleich gross sein
		// bestimme ihr aussehen in des CSS Datei
		
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER)
				this.fire();
		});
		
		// TODO: der Wert von defaultButtonWidth sollte sich dem Ger�te-BS anpassen k�nnen
	}
}
