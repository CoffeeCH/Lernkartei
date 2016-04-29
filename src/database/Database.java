package database;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTextField;

import cards.EditCard;


public class Database {
	public static ResultSet	rs;
	public static String	insert;

	// Varibeln Connection

	private static String	windowsUser	= System.getProperty("user.name");
	private static String	url			= "jdbc:sqlite:" + windowsUser + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. R�ckseite, 3.
	 *            Set_ID, 4. Priorit�t (1-5), 5. Color
	 */

	public static void pushToStock (String[] values) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);

			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Backside       TEXT    NOT NULL, " +
					" Frontside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			insert = "INSERT INTO Stock (Backside, Frontside, Set_ID, Priority, Color)" +
					"VALUES ('" + values[0] + "','" + values[1] + "','" + values[2] + "', '" + values[3] + "', '"
					+ values[4] + "')";

			System.out.println(insert);
			stmt.executeUpdate(insert);

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         R�ckseite, Description, Set_ID, Priorit�t, Farbe
	 */

	public static ArrayList<String[]> pullFromStock () {

		ArrayList<String[]> results = new ArrayList<String[]>();
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Stock;");

			while (rs.next()) {

				String[] set = new String[7];
				set[0] = Integer.toString(rs.getInt("PK_Stk"));
				set[1] = rs.getString("Backside");
				set[2] = rs.getString("Frontside");
				set[3] = rs.getString("Description");
				set[4] = Integer.toString(rs.getInt("Set_ID"));
				set[5] = Integer.toString(rs.getInt("Priority"));
				set[6] = rs.getString("Color");
				results.add(set);

			}

			rs.close();
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return results;

	}

	public static void delEntry (String id) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String del = "DELETE FROM Stock WHERE PK_Stk = " + id;

			stmt.executeUpdate(del);
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static ArrayList<String> getEdited () {

		ArrayList<String> swalues = new ArrayList<String>();

		for (JTextField s : EditCard.addJFs) {
			swalues.add(s.getText());
		}

		return swalues;

	}

	public static void delStock () {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String del = "DROP TABLE IF EXISTS Stock";

			stmt.executeUpdate(del);
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * Erh�ht die Priorit�t um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erh�ht wird
	 */

	public static void updatePrio (Integer PK_ID) {

		Connection c = null;
		Statement stmt = null;
		String oldPrio = "";
		String newPrio = "";

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			c.setAutoCommit(false);
			ResultSet actualPrio = stmt.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());

			if (actualPrio.next()) {
				oldPrio = Integer.toString(actualPrio.getInt("Priority"));
				actualPrio.close();
			}
			else {
				System.out.println("No Card with this ID exists.");
				actualPrio.close();
			}

			if (oldPrio.equals("5")) {
				newPrio = "5";
			}
			else {
				newPrio = oldPrio + 1;
			}

			c.setAutoCommit(true);
			String updatePrio = "UPDATE Stock SET Priority = ID WHERE PK_Stk = " + newPrio;
			System.out.println(updatePrio);
			stmt.executeUpdate(updatePrio);
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static void resetPrio (String karte) {

		// TODO: Funktion, welche Priorit�t auf 1 setzt.

	}

}
