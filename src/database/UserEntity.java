package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.sql.Attribute;
import database.sql.AttributeList;
import database.sql.Entity;
import database.sql.SQLHandler;
import debug.Logger;

public class UserEntity extends Entity
{

	// URL und Driver

	/**
	 * @param tabName
	 */
	public UserEntity(String tabName)
	{
		super(tabName, tabName + "_PK");
		// set table attributes
		Attribute a = new Attribute("ActualScore", 0);
		myAttributes.add(a);
		a = new Attribute("Username", "def");
		myAttributes.add(a);
		a = new Attribute("Email");
		myAttributes.add(a);
		a = new Attribute("Password"); // als Passwort wird der gesalzene Hash
										// gespeichert
		myAttributes.add(a);
		a = new Attribute("Salz"); // Hier wird das Salz gespeichert
		myAttributes.add(a);
		a = new Attribute("HighScore", 0);
		myAttributes.add(a);
		a = new Attribute("UserType", 0);
		myAttributes.add(a);
		createTableIfNotExists();
	}

	// private static String url = "jdbc:sqlite:" +
	// globals.Environment.getDatabasePath()
	// + globals.Globals.db_name + ".db";
	// private static String driver = "org.sqlite.JDBC";
	//
	private static Integer anzahlLeben;
	private static Integer currentLifes;

	/**
	 * 
	 * Fragt den Score einer Kartei ab
	 * 
	 * @param Kartei
	 *            --> Welche Kartei, welche abgefragt werden soll
	 * @return --> Returned einen Double Wert des Scores, returned -1, wenn kein
	 *         Score vorhanden
	 */
	public void correctCard()
	{
		try
		{
			Integer currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			// String getCurrent = "SELECT Lifecount FROM Lifes";
			if (getLastResultSet().next())
			{
				currentLifes = getLastResultSet().getInt("ActualScore");
				getLastResultSet().close();
			} else
			{
				getLastResultSet().close();
				myAttributes.seekKeyNamed("ActualScore").setValue(0);
				setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes));
				// String newEntry = "INSERT INTO Lifes (Lifecount) VALUES (0)";
			}
			myAttributes.seekKeyNamed("ActualScore").setValue(currentLifes + 1);
			Attribute k = new Attribute("Username", "def");
			setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
			// String updt = "UPDATE Lifes SET Lifecount = " + (currentLifes +
			// 1);
		} catch (Exception e)
		{
			Logger.out(e.getMessage());
		}
	}

	public int getLifecount()
	{
		try
		{
			Integer currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			// String getCurrent = "SELECT Lifecount FROM Lifes";
			if (getLastResultSet().next())
			{
				currentLifes = getLastResultSet().getInt("ActualScore");
			}
			getLastResultSet().close();
			float notRounded = currentLifes / 30;
			anzahlLeben = Math.round(notRounded);
		} catch (Exception e)
		{
			Logger.out(e.getMessage());
		}
		return anzahlLeben;
	}

	public void death()
	{
		try
		{
			Integer currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next())
			{
				currentLifes = getLastResultSet().getInt("ActualScore");
			}
			if (currentLifes >= 30)
			{
				myAttributes.seekKeyNamed("ActualScore").setValue(currentLifes - 30);
				Attribute k = new Attribute("Username", "def");
				setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
			}
		} catch (Exception e)
		{
			Logger.out(e.getMessage());
		}
	}

	public int getCorrectCards()
	{
		try
		{
			currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next())
			{
				currentLifes = getLastResultSet().getInt("ActualScore");
			}
		} catch (Exception e)
		{
			Logger.out(e.getMessage());
		}
		return currentLifes;
	}

	// �berpr�ft, ob ein Eintrag bereits vorhanden ist gesteuert mit dem
	// Usernamen. Somit ist der Username einmalig
	public boolean checkDatabase(String attribut, String nameToCheck)
	{
		try
		{
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), attribut, attribut, nameToCheck));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next())
			{
				return true;
			}
		} catch (Exception e)
		{
			Logger.out(e.getMessage());
		}
		return false;
	}
	
	/**
	 * insertIntoTableCommand(String tabName, AttributeList attributes, String att1, String val1, String att2, String val2)
	 * @param attribute Welche Eigenschaft 
	 * @param keyName
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public boolean setData(String attribute,String newValue) {
		boolean worked = false;
		try {
			setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(),myAttributes, attribute, newValue, null, null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			
			worked = true;
			
			getLastResultSet().close();
		}
		catch (Exception e) {
			Logger.log(e.getMessage());
		}
		return worked;
	}
	
	
	public String loadData(String attribute, String username) {
		setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), attribute, attribute, username));
		setLastResultSet(executeQuery(getLastSQLCommand()));
		try
		{
			if (getLastResultSet().next())
				return getLastResultSet().getString(attribute);
			return "leer";
		} catch (SQLException e)
		{
			Logger.log(e.getMessage());
			return "leer";
		}
	}
}
	
	
// BEREITS IN ENTITY VORHANDEN!!! ---> Entity.seekInTable();
//	/**
//	 * Nur geeignet, wenn man einzelne Werte suchen will. zum Beispiel einen Namen, eine E-Mail o.�., weil nur ein String returniert wird.
//	 * @param attribute Die Eigenschaft, welche man auslesen m�chte. 
//	 * @param seekKeyName Die zu �berpr�fende Eigenschaft
//	 * @param seekKeyValue Der Wert, den die �berpr�fung liefern soll
//	 * @return Das Resultat, welches aus dem Command resultiert, wenn man die Parameter mit dem SQLHandler zusammensetzt.
//	 */
//	public String getUserData(String attribute, String seekKeyName, String seekKeyValue) {
//		setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), attribute, seekKeyName, seekKeyValue));
//		setLastResultSet(executeQuery(getLastSQLCommand()));
//		try
//		{
//			if (getLastResultSet().next())
//				return getLastResultSet().getString(attribute);
//			return "leer";
//		} catch (SQLException e)
//		{
//			Logger.log(e.getMessage());
//			return "leer";
//		}

//BRAUCHT ES NICHT MEHR WEIL JETZT UserEntity.loadData(attribute, username) ZUST�NDIG
//	// ladet den Hash -> Passwort und Salt gehasht
//	public String getHash(String username)
//	{
//		setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "Password", "Username", username));
//		setLastResultSet(executeQuery(getLastSQLCommand()));
//		try
//		{
//			if (getLastResultSet().next())
//			{
//				return getLastResultSet().getString("Password");
//			}
//			return "leer";
//		} catch (SQLException e)
//		{
//			Logger.log(e.getMessage());
//			return "leer";
//		}
//	}
//
//	// ladet das Salt
//	public String getSalt(String username)
//	{
//		setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "Salz", "Username", username));
//		setLastResultSet(executeQuery(getLastSQLCommand()));
//		try
//		{
//			if (getLastResultSet().next())
//			{
//				return getLastResultSet().getString("Salz");
//			}
//			return "leer";
//		} catch (SQLException e)
//		{
//			Logger.log(e.getMessage());
//			return "leer";
//		}
//	}
	

