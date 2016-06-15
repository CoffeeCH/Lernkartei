package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import debug.Logger;

/**
 * 
 * @author Joel H�berli
 *
 * 		@WhatIsThis? Gibt Daten f�r DataModel aus, und kann neue verabrbeiten
 */

public class UserCards {

	// URL und Driver

	private static String	url			= "jdbc:sqlite:" +  globals.Environment.getDatabasePath()
	 									 + globals.Globals.db_name + ".db";
	private static String	driver		= "org.sqlite.JDBC";
	
	public UserCards () {

		// f�r Sp�ter, damit sichergestellt ist, dass die Tabelle "Score"
		// existiert
		Connection c = null;
		Statement stmt = null;

		try {

			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Score 	(PK_Score INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "Kartei TEXT NOT NULL,"
					+ "Score REAL NOT NULL);";
			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);
		}
		catch (Exception e) {
			Logger.log("UserCards Constructor: " + e.getMessage()); 
		}
	}

	/**
	 * 
	 * Fragt die Karteien eines Users ab
	 * 
	 * @return --> Returned eine ArrayList mit Karteien, returnt eine leere,
	 *         wenn keine Kartei vorhanden
	 */

	private static ArrayList<String> listCards = new ArrayList<String>();

	public static ArrayList<String> getCards () {

		Connection c = null;
		Statement stmt = null;
		listCards.clear();

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			String Karten = "SELECT Kartei FROM Score";
			ResultSet Cards = stmt.executeQuery(Karten);

			String testEintrag = Cards.getString(Cards.findColumn("Kartei"));

			debug.Debugger.out(testEintrag);

			Cards.afterLast();
			int letzterEintrag = Cards.getRow() - 1;

			for (int i = 1; i < letzterEintrag; i++) {
				listCards.add(Cards.getString(i));
			}
			return listCards;

		}
		catch (Exception e) {
			Logger.log("Database.getCards(): " + e.getMessage());
			listCards.clear();
			return listCards;
		}

	}

	boolean wasSuccessful;

	public boolean addCards (String query) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			stmt.executeUpdate(query);
		}
		catch (Exception e) {
			Logger.log("UserCards.addCards(): " + e.getMessage());
		}
		
		return wasSuccessful;
	}

}
