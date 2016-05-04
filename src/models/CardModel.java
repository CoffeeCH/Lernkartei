package models;

import java.util.ArrayList;

import application.Constants;
import database.Database;
import mvc.Model;

public class CardModel extends Model {

	public CardModel (String myName) {
		super(myName);
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD) {
		
		// Returns:
		//  0: Falsche Eingabe --> functionName existiert nicht
		//  1: Erfolgreich hinzugef�g
		// -1: Fehler beim hinzuf�gen der Karte
		// 	2: Erfolgreich gel�scht
		// -2: Nicht gel�scht
		//  3: Erfolgreich bearbeitet
		// -3: Bearbeiten fehlgeschlagen, ID nicht gefunden
		
		if (functionName.equals("new")) {
			
			// paramS = Vorderseite:::R�ckseite:::SetName
			
			if (paramS.contains("'"))
			{
				paramS = paramS.replaceAll("'", "''");
			}
			
			String[] values = new String[5];
			values[0] = paramS.split(application.Constants.SEPARATOR)[0];
			values[1] = paramS.split(application.Constants.SEPARATOR)[1];
			values[2] = paramS.split(application.Constants.SEPARATOR)[2];
			values[3] = "1";
			values[4] = "-16777216"; // Standart Farbcode f�r Schwarz
			
			if (Database.pushToStock(values)) {
				refreshViews();
				return 1;
			} else {
				return -1;
			}
			
		} else if (functionName.equals("edit")) {
			
			if (Database.editEntry (paramS.split(Constants.SEPARATOR)[0], 
								paramS.split(Constants.SEPARATOR)[1], 
								paramS.split(Constants.SEPARATOR)[2])) {
				return  3;
			} else {		
				return -3;
			}
						
		} else if (functionName.equals("delete")) {
			
			// paramS = KartenID
			
			if (Database.delEntry(paramS)) {
				refreshViews();
				return 2;
			} else {
				return -2;
			}
			
		}
		
		return 0;
	}

	@Override
	public ArrayList<String> getDataList (String query) {

		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String[]> cards = Database.pullFromStock(query);
		if (cards == null) { 
			debug.Debugger.out("getData cards = null");
			return result;
		
		} else {
			
			for (String[] s : cards) {
				debug.Debugger.out(s[0]);
				String data = s[0] + application.Constants.SEPARATOR 
							+ s[1] + application.Constants.SEPARATOR 
							+ s[2];
				
				result.add(data);
				
			}
			
			return result;
			
		}
		
	}
}
