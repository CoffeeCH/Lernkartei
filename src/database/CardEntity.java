package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;
import database.sql.KeyAttribute;
import database.sql.SQLHandler;
import debug.Logger;

public class CardEntity extends Entity {

	/**
	 * @param myNewTableName
	 */
	public CardEntity(String tabName) {
		super(tabName,"PK_"+tabName);
		// set table attributes
		Attribute a = new Attribute("Frontside");
		myAttributes.add(a);
		a = new Attribute("Backside");
		myAttributes.add(a);
		a = new Attribute("Description");
		myAttributes.add(a);
		a = new Attribute("Date");
		myAttributes.add(a);
		a = new Attribute("Color");
		myAttributes.add(a);
		KeyAttribute k = new KeyAttribute("Priority",0,"1");
		myAttributes.add(k);
		ForeignKey f = new ForeignKey("PK_STACK");
		myAttributes.add(f);
		createTableIfNotExists();
	}

//	protected static String myFKName     = "Set_ID";	
//	private   static String mySeekAttribute = "Priority";
//				myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//				" Frontside     	TEXT    NOT NULL, " +
//				" Backside      	TEXT    NOT NULL, " +
//				" "+myFKName+"    	INTEGER NOT NULL, " +
//				" "+mySeekAttribute+"	INTEGER DEFAULT 1," +
//				" Description   	TEXT    		, " +
//				" Datum				TEXT";
		
//	private static String pushSql = "CREATE TABLE IF NOT EXISTS Stock " + "(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT,"
//			+ " Frontside       TEXT    NOT NULL, " + " Backside      TEXT    NOT NULL, "
//			+ " Set_ID    		INTEGER NOT NULL, " + " Priority	    INTEGER DEFAULT 1,"
//			+ " Description    TEXT    		, " + " Color			TEXT    		 )";
//
//	public static String getDbURL() {
//		return dbURL;
//	}
//
	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. R�ckseite, 3.
	 *            Set_ID, 4. Priorit�t (1-5), 5. Color
	 * @  deprecated
	 */
	public boolean pushToStock (String[] values) {
		try {
			// TO DO should accept attribute list only 
			ResultSet selectSet = seekInTable("Door", values[2]);
//			ResultSet selectSet = stmt
//			.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + values[2] + "'");
//			
			String setID;
			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt("PK_Kategorie"));
				selectSet.close();
			} else {
				selectSet.close();
				Logger.out("no Door's in database for Stack"+values[2]+"!",this.getMyTableName());
				return false;
			}
			String attributeList = myAttributes.getCommaSeparatedList();
			values[2] = setID;
			int inserts = insertIntoTable(attributeList, values);
//			String insert = "INSERT INTO Stock (Frontside, Backside, Set_ID, Priority, Color)" + "VALUES ('" + values[0]
//					+ "','" + values[1] + "'," + setID + ", " + values[3] + ", '" + values[4] + "')";
//			stmt.executeUpdate(insert);
			if (inserts > 0 ) return true;
			else 
				Logger.out("no inserts could be performed!",this.getMyTableName());
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return false;

	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         R�ckseite, Description, Set_ID, Priorit�t, Farbe
	 */

	public ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();

		try {
			setLastResultSet(seekInTable("STACK", whichSet));
			//ResultSet s = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + whichSet + "'");
			String ID_SET="0";
			if (getLastResultSet().next()) {
				ID_SET = Integer.toString(getLastResultSet().getInt("PK_STACK"));
				getLastResultSet().close();
			} else {
				Logger.out("no Stocks's in database for "+whichSet+"!",getMyTableName());
				getLastResultSet().close();
				return null;
			}
			
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),null,"PK_STACK",ID_SET)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//ResultSet rs = stmt.executeQuery("SELECT * FROM Card WHERE Set_ID = '" + IDwhichSet + "'");
			while (getLastResultSet().next()) {
				String[] set = new String[7];
				set[0] = Integer.toString(getLastResultSet().getInt("PK_CARD"));
				set[1] = getLastResultSet().getString("Frontside");
				set[2] = getLastResultSet().getString("Backside");
				set[3] = getLastResultSet().getString("Description");
				set[4] = Integer.toString(getLastResultSet().getInt("PK_STACK"));
				set[5] = Integer.toString(getLastResultSet().getInt("Priority"));
				set[6] = getLastResultSet().getString("Color");
				results.add(set);
			}
			getLastResultSet().close();
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return results;
	}

	public boolean delEntry(String id) {
		setLastSQLCommand(SQLHandler.deleteEntryCommand("STACK", "PK_STACK", id)); 
		return (executeCommand(getLastSQLCommand())>=0)?true:false;
	}

	/**
	 * �berschreibt Werte in der Datenbank um Karten zu editieren.
	 * 
	 * @param id
	 *            --> Welche Karte mit ID soll ge�ndert werden
	 * @param frontside
	 *            --> Welcher Text als Vorderseite
	 * @param backside
	 *            --> Welcher Text als R�ckseite
	 * @return --> True: Funktionierte, False: Nicht geklappt
	 */

	public boolean editEntry(String id, String frontside, String backside) {

		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),null,"PK_STACK",id)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//String sel = "SELECT * FROM Stock WHERE PK_Stk = " + id;
			if (!getLastResultSet().next()) {
				getLastResultSet().close();
				return false;
			}
			else {
				getLastResultSet().close();
			}

			//String del = "UPDATE Stock SET Frontside = '" + frontside + "', Backside = '" + backside
			//		+ "' WHERE PK_Stk = " + id;
			return true;
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return false;
	}

	/**
	 * Erh�ht die Priorit�t um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erh�ht wird
	 */

	public void upPrio (Integer PK_ID) {

		Integer oldPrio = null;
		String newPrio = "";
		try {
			// Frage die Aktuelle Priorit�t ab
			ResultSet actualPrio = stmt.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());

			// �berpr�ft ob vorhanden oder nicht
			if (actualPrio.next()) {
				oldPrio = actualPrio.getInt(myAttributes.getKeyName());
				actualPrio.close();
			}
			else {
				debug.Debugger.out("No Card with "+myAttributes.getKeyName()+"='"+PK_ID.toString()+"' exists.");
				actualPrio.close();
			}

			// Wenn Aktuelle Priorit�t = 5, bleibt die neue bei 5, sonst wird
			// sie um 1 erh�ht

			if (oldPrio == 5) {
				newPrio = "5";
			}
			else {
				newPrio = Integer.toString(oldPrio + 1);
			}

			// Schreibt die Neue Priorit�t in die Datenbank

			String updatePrio = "UPDATE Stock SET Priority = " + newPrio + " WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);
		}
		catch (Exception e) {
			Logger.log("Database.upPrio("+PK_ID+"): "+e.getMessage());
		}
	}

	/**
	 * Bei nicht wissen der Karte, wird die Prio zur�ckgesetzt --> Sprich auf 0
	 * gesetzt
	 * 
	 * @param karte
	 *            --> Welche Karte reseted wird
	 */
	public void resetPrio (Integer PK_ID) {
		try {
			// Setzt die Priorit�t zur�ck auf 
			String updatePrio = "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID;
			executeQuery(updatePrio);
		}
		catch (Exception e) {
			Logger.log("Database.resetPrio("+PK_ID+"): "+e.getMessage());
		}
	}
	
	/**
	 * Liefert die Priorit�t der Karte mit mitgegebener ID mit 
	 * 
	 * @param ID_Card --> ID der Karte, von welcher die Priorit�t gebraaucht wird
	 * @return --> Gibt die Kartenpriorit�t als Integer zur�ck
	 */
	public int getPriority (String ID_Card) {
		int prio = 0;
		try {
			String getPrio = "SELECT Priority FROM Stock WHERE PK_Stk = " + ID_Card;
			ResultSet rsPrio = stmt.executeQuery(getPrio);

			if (rsPrio.next()) {
				prio = rsPrio.getInt("Priority");
			} else {
				debug.Debugger.out("No such Cards exists in stock @ ID ("+ID_Card+")!");
			}
		}
		catch (Exception e) {
			Logger.log("Database.getPriority("+ID_Card+"): "+e.getMessage());
		}
		return prio;
	}
	
	/**
	 * Liefert den Maximalen und den bisher erreichten Score eines Stacks zur�ck
	 *  
	 * @param whichSet --> Score von welchem Stack geliefert werden soll
	 * @return --> Retourniert diesen gew�nschten Score
	 */
	public Double[] getScore (String whichSet) {

		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];
		try {
			// Alle Priorit�ten aus Tabelle hlen, welche als Set das mitgegebene
			// haben.

			String getScore = "SELECT Priority FROM Stock WHERE Set_ID = (SELECT PK_Kategorie FROM Kategorie"
					+ " WHERE Kategorie = '" + whichSet + "')";
			ResultSet scrs = stmt.executeQuery(getScore);
			// Durch loopen und die Maximale sowie die Erreichte Punktzahl
			// speichern

			if (scrs.next()) {
				maxPoints += 4.0;
				reachedPoints += scrs.getInt("Priority") - 1.0;
				while (scrs.next()) {
					maxPoints += 4.0;
					reachedPoints += scrs.getInt("Priority") - 1.0;
				}

			} else {
				return null;
			}
		}
		catch (Exception e) {
			Logger.log("Database.getScore("+whichSet+"): "+e.getMessage());
		}
		// Erreichte Punktzahl zur�ckgeben
		score[0] = maxPoints;
		score[1] = reachedPoints;
		return score;
	}

	public String[] getFrontAndBackside(String Stack, int kartenID) {

		ArrayList<String[]> cards = pullFromStock(Stack);

		String vorderseite = cards.get(kartenID)[1];
		String r�ckseite = cards.get(kartenID)[2];

		String[] VorderUndR�ckseite = { vorderseite, r�ckseite };

		return VorderUndR�ckseite;
	}
}
