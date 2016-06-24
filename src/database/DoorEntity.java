package database;

import java.util.ArrayList;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.SQLHandler;
import debug.Logger;

public class DoorEntity extends Entity {

	DoorEntity (String tabName) {
		super(tabName, tabName+"_PK");
		// set table attributes
		Attribute a = new Attribute("Name");
		myAttributes.add(a);
		createTableIfNotExists();
	}

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
	public boolean newDoor (String eingabe) {
		boolean worked = false;
		// Do "SELECT Doorname FROM Doors WHERE Doorname = " + "'" + eingabe + "'"
		setLastSQLCommand(SQLHandler.selectCommand(	this.getMyTableName(),"name","name",eingabe)); 
		setLastResultSet(executeQuery(getLastSQLCommand()));
		try {
			// �berpr�ft, ob bereits ein Eintrag mit dem Selben Namen enthalten ist
			if (!getLastResultSet().next()) {
				getLastResultSet().close();
				// Einf�gen des Datensatzes in Doors
				myAttributes.seekKeyNamed("name").setValue(eingabe);
				setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes)); 
				// Do "INSERT INTO Doors (Doorname)" + "VALUES ('" + eingabe + "')";
				worked = (this.executeCommand(getLastSQLCommand()) >= 0) ? true:false;
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}

	/**
	 * 
	 * Methode, welche alle T�ren in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen T�rennamen
	 */
 	public ArrayList<String> getDoors () {
		ArrayList<String> data = new ArrayList<String>();
		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			while (getLastResultSet().next()) {
				String name = "";
				name = getLastResultSet().getString("name");
				data.add(name);
			}
			getLastResultSet().close();
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
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
	public boolean delDoor (String delName) {
		boolean worked = false;
		ArrayList<String> setsToDel = new ArrayList<String>();
		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"PK_DOOR","name", delName)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				int doorID = getLastResultSet().getInt("PK_DOOR");
				getLastResultSet().close();

				// Do "SELECT * FROM Kategorie WHERE PK_Door = " + doorID
				setLastSQLCommand(SQLHandler.selectCommand(	"STACK",null,"PK_DOOR",doorID)); 
				setLastResultSet(executeQuery(getLastSQLCommand()));
				while (getLastResultSet().next()) {
					setsToDel.add(getLastResultSet().getString("name"));
				}
				getLastResultSet().close();
				for (String s : setsToDel) {
					LKDatabase.myStacks.delStack(s);
				}
				setLastSQLCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "PK_DOOR", doorID)); 
				executeCommand(getLastSQLCommand());
				// Do "DELETE FROM Doors WHERE Doorname = '" + delName + "'";
				setLastSQLCommand(SQLHandler.deleteEntryCommand("STACK", "PK_DOOR", doorID)); 
				executeCommand(getLastSQLCommand());
				
				// TODO Delete all cards of those stacks...
				// Do "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
//				setLastSQLCommand(SQLHandler.deleteEntryCommand("STACK", "PK_DOOR", doorID)); 
//				setLastResultSet(executeQuery(getLastSQLCommand()));
				//String delSets = "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
				worked = true;
			}
			else {
				getLastResultSet().close();
				worked = false;
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}
	
	public boolean update(String oldName, String newName) {
		boolean worked = true;
		try {
			// Do "SELECT * FROM Doors WHERE Doorname = '" + oldName + "';"
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"name","name",oldName)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				Attribute k = new Attribute("name",oldName);
				// Do "UPDATE Doors SET Doorname = '" + newName + "' WHERE Doorname = '" + oldName + "';"
				setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(),myAttributes,k)); 
				executeCommand(getLastSQLCommand());
				worked = true;
			} else {
				worked = false;
			}
			getLastResultSet().close();
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}
}
