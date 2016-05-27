package database;

import java.sql.*;
import java.util.ArrayList;

import debug.Logger;


public class Database extends SQLiteConnector {

	// Varibeln Connection

	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
			+ globals.Globals.db_name + ".db";
	private static String	driver	= "org.sqlite.JDBC";

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. R�ckseite, 3.
	 *            Set_ID, 4. Priorit�t (1-5), 5. Color
	 */

	public static boolean pushToStock (String[] values) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Frontside       TEXT    NOT NULL, " +
					" Backside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			String setID;

			c.setAutoCommit(false);
			ResultSet selectSet = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '"
					+ values[2] + "'");
			c.setAutoCommit(true);

			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt("PK_Kategorie"));
				selectSet.close();
			}
			else {
				selectSet.close();
				stmt.close();
				c.close();
				return false;
			}

			String insert = "INSERT INTO Stock (Frontside, Backside, Set_ID, Priority, Color)" +
					"VALUES ('" + values[0] + "','" + values[1] + "'," + setID + ", " + values[3] + ", '"
					+ values[4] + "')";

			stmt.executeUpdate(insert);
			closeDB();
			return true;
		}
		catch (Exception e) {
			Logger.log("Database.pushToStock(): " + e.getMessage());
		}
		closeDB();
		return false;

	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         R�ckseite, Description, Set_ID, Priorit�t, Farbe
	 */

	public static ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Frontside       TEXT    NOT NULL, " +
					" Backside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			c.setAutoCommit(false);
			String IDwhichSet = "";
			ResultSet s = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + whichSet + "'");
			c.setAutoCommit(true);
			
			if (s.next()) {
				IDwhichSet = Integer.toString(s.getInt("PK_Kategorie"));
			}
			else {
				debug.Debugger.out("No Kategorie: " + whichSet + "in Table Kategorie");
				closeDB();
				return null;
			}

			s.close();
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Stock WHERE Set_ID = '" + IDwhichSet + "'");
			c.setAutoCommit(true);
			
			while (rs.next()) {

				String[] set = new String[7];
				set[0] = Integer.toString(rs.getInt("PK_Stk"));
				set[1] = rs.getString("Frontside");
				set[2] = rs.getString("Backside");
				set[3] = rs.getString("Description");
				set[4] = Integer.toString(rs.getInt("Set_ID"));
				set[5] = Integer.toString(rs.getInt("Priority"));
				set[6] = rs.getString("Color");
				results.add(set);

			}

			rs.close();
		}
		catch (Exception e) {
			Logger.log("Database.pullFromStock(): " + e.getMessage());
		}
		closeDB();
		return results;

	}

	public static boolean delEntry (String id) {

		Connection c = null;
		Statement stmt = null;
		boolean deleted = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String del = "DELETE FROM Stock WHERE PK_Stk = " + id;
			stmt.executeUpdate(del);
			deleted = true;
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		closeDB();
		return deleted;

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

	public static boolean editEntry (String id, String frontside, String backside) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			c.setAutoCommit(false);
			String sel = "SELECT * FROM Stock WHERE PK_Stk = " + id;
			ResultSet rs = stmt.executeQuery(sel);
			c.setAutoCommit(true);

			if (!rs.next()) {
				rs.close();
				closeDB();
				return false;
			}
			else {
				rs.close();
			}
			c.setAutoCommit(true);
			String del = "UPDATE Stock SET Frontside = '" + frontside
					+ "', Backside = '" + backside
					+ "' WHERE PK_Stk = " + id;

			debug.Debugger.out(del);
			stmt.executeUpdate(del);
		}
		catch (Exception e) {
			Logger.log("Database.editEntry(): " + e.getMessage());
		}
		closeDB();
		return true;

	}

	/**
	 * Erh�ht die Priorit�t um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erh�ht wird
	 */

	public static void upPrio (Integer PK_ID) {

		Connection c = null;
		Statement stmt = null;
		Integer oldPrio = null;
		String newPrio = "";

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Frontside       TEXT    NOT NULL, " +
					" Backside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);


			// Frage die Aktuelle Priorit�t ab
			c.setAutoCommit(false);
			ResultSet actualPrio = stmt.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());
			c.setAutoCommit(true);
			
			// �berpr�ft ob vorhanden oder nicht

			if (actualPrio.next()) {
				oldPrio = actualPrio.getInt("Priority");
				actualPrio.close();
			}
			else {
				debug.Debugger.out("No Card with this ID exists.");
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
			Logger.log("Database.upPrio(): " + e.getMessage());
		}
		closeDB();
	}

	/**
	 * Bei nicht wissen der Karte, wird die Prio zur�ckgesetzt --> Sprich auf 0
	 * gesetzt
	 * 
	 * @param karte
	 *            --> Welche Karte reseted wird
	 */

	public static void resetPrio (Integer PK_ID) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			// Setzt die Priorit�t zur�ck auf 1

			String updatePrio = "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);
		}
		catch (Exception e) {
			Logger.log("Database.resetPrio(): " + e.getMessage());
		}
		closeDB();
	}
	
	/**
	 * Liefert die Priorit�t der Karte mit mitgegebener ID mit 
	 * 
	 * @param ID_Card --> ID der Karte, von welcher die Priorit�t gebraaucht wird
	 * @return --> Gibt die Kartenpriorit�t als Integer zur�ck
	 */
	
	public static int getPriority (String ID_Card) {
		
		Connection c = null;
		Statement stmt = null;

		int prio = 0;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			

			String getPrio = "SELECT Priority FROM Stock WHERE PK_Stk = " + ID_Card;
			c.setAutoCommit(false);
			ResultSet rsPrio = stmt.executeQuery(getPrio);
			c.setAutoCommit(true);
			
			if (rsPrio.next()) {
				prio = rsPrio.getInt("Priority");
			} else {
				debug.Debugger.out("No such Card exists!");
			}
		}
		catch (Exception e) {
			Logger.log("Database.getPriority(): " + e.getMessage());
		}
		closeDB();
		return prio;
		
	}
	
	/**
	 * Liefert den Maximalen und den bisher erreichten Score eines Stacks zur�ck
	 *  
	 * @param whichSet --> Score von welchem Stack geliefert werden soll
	 * @return --> Retourniert diesen gew�nschten Score
	 */
	
	public static Double[] getScore (String whichSet) {

		Connection c = null;
		Statement stmt = null;

		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			

			// Alle Priorit�ten aus Tabelle hlen, welche als Set das mitgegebene
			// haben.

			String getScore = "SELECT Priority FROM Stock WHERE Set_ID = (SELECT PK_Kategorie FROM Kategorie"
					+ " WHERE Kategorie = '" + whichSet + "')";
			c.setAutoCommit(false);
			ResultSet scrs = stmt.executeQuery(getScore);
			c.setAutoCommit(true);
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
				closeDB();
				return null;
			}
		}
		catch (Exception e) {
			Logger.log("Database.getPriority(): " + e.getMessage());
		}

		// Erreichte Punktzahl zur�ckgeben
		
		score[0] = maxPoints;
		score[1] = reachedPoints;
		closeDB();
		return score;

	}

}
