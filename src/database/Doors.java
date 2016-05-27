package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import debug.Logger;

public class Doors {

	// URL und Driver

	private static String	url			= "jdbc:sqlite:" +  globals.Environment.getDatabasePath()
										 + globals.Globals.db_name + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * Methode, zum Erstellen einer neuen T�re
	 * 
	 * @param eingabe
	 *            --> String Name der T�r
	 * 
	 * @return --> True, wenn Eintrag eingef�gt, false, wenn Eintrag bereits
	 *         vorhanden
	 *
	 */

	public static boolean newDoor (String eingabe) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Doors " +
					"(PK_Doors INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Doorname TEXT NOT NULL)";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			// �berpr�ft, ob bereits ein Eintrag mit dem Selben Namen enthalten
			// ist
			
			c.setAutoCommit(false);
			ResultSet checkName = stmt
					.executeQuery("SELECT Doorname FROM Doors WHERE Doorname = " + "'" + eingabe + "'");
			c.setAutoCommit(true);
			
			if (!checkName.next()) {

				checkName.close();
				
				// Einf�gen des Datensatzes in Doors

				String insert = "INSERT INTO Doors (Doorname)" +
						"VALUES ('" + eingabe + "')";

				stmt.executeUpdate(insert);
				stmt.close();
				c.close();
				
				worked = true;

			}
			else {

				worked = false;
				stmt.close();
				c.close();

			}

		}
		catch (Exception e) {
			Logger.log("Database.newDoor(): " + e.getMessage());
		}

		return worked;

	}

	/**
	 * 
	 * Methode, welche alle T�ren in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen T�rennamen
	 */

	public static ArrayList<String> getDoors () {

		Connection c = null;
		Statement stmt = null;

		ArrayList<String> data = new ArrayList<String>();

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			c.setAutoCommit(false);
			ResultSet tbl = stmt.executeQuery("SELECT tbl_name FROM sqlite_master WHERE type='table' AND tbl_name='Doors'");
			c.setAutoCommit(true);			
			
			if (tbl.next()) {
				c.setAutoCommit(false);
				ResultSet rs = stmt.executeQuery("SELECT Doorname FROM Doors");
				c.setAutoCommit(true);
				
				while (rs.next()) {

					String name = "";
					name = rs.getString("Doorname");
					data.add(name);

				}

				rs.close();
				stmt.close();
				c.close();

			}
			else {

				debug.Debugger.out("Table Doors is not created yet.");
				stmt.close();
				c.close();

			}
		}
		catch (Exception e) {
			Logger.log("Database.getDoors(): " + e.getMessage());
		}

		return data;

	}

	/**
	 * Gibt boolean zur�ck, obs funktioniert hat oder nicht
	 * 
	 * @param delName
	 *            --> Name, welcher gel�scht werden soll
	 * @return --> True, Gel�scht / false, nicht Gel�scht / vorhanden
	 */

	public static boolean delDoor (String delName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;
		ArrayList<String> setsToDel = new ArrayList<String>();
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			c.setAutoCommit(false);
			ResultSet del = stmt.executeQuery("SELECT * FROM Doors WHERE Doorname = '" + delName + "'");
			c.setAutoCommit(true);
			
			if (del.next()) {
				Integer doorID = del.getInt("PK_Doors");
				del.close();

				c.setAutoCommit(false);
				ResultSet getStacks = stmt.executeQuery("SELECT * FROM Kategorie WHERE FK_Door = " + doorID);
				c.setAutoCommit(true);
				
				while (getStacks.next()) {
					setsToDel.add(getStacks.getString("Kategorie"));
				}
				
				getStacks.close();
				for (String s : setsToDel) {
					database.Stack.delStack(s);
				}
				c.setAutoCommit(true);
				
				String delDoor = "DELETE FROM Doors WHERE Doorname = '" + delName + "'";
				String delSets = "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
				
				stmt.executeUpdate(delDoor);
				stmt.executeUpdate(delSets);
				stmt.close();
				c.close();
				worked = true;
			}
			else {
				del.close();
				stmt.close();
				c.close();
				worked = false;
			}

		}
		catch (Exception e) {
			Logger.log("Database.delDoor(): " + e.getMessage());
		}

		return worked;

	}
	
	public static boolean update(String oldName, String newName) {
		
		Connection c = null;
		Statement stmt = null;
		boolean worked = true;
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			c.setAutoCommit(false);
			
			ResultSet checkDoor = stmt.executeQuery("SELECT * FROM Doors WHERE Doorname = '" + oldName + "';");
			c.setAutoCommit(true);
			if (checkDoor.next()) {
				String updateDoor = "UPDATE Doors SET Doorname = '" + newName + "' WHERE Doorname = '" + oldName + "';";
				stmt.executeUpdate(updateDoor);
				worked = true;
				checkDoor.close();
			} else {
				worked = false;
				checkDoor.close();
			}
			
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			Logger.log("Database.update(): " + e.getMessage());
		}
		
		return worked;
		
	}

}
