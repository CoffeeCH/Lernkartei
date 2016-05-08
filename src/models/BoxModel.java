package models;

import java.util.ArrayList;

import mvc.Model;


public class BoxModel extends Model
{
	public BoxModel (String myName)
	{
		super(myName);
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		// +0: Falsche Parameter in doAction
		// +1: Erfolgreich erstellt
		// +2: Erfolgreich gel�scht
		// -1: Keine T�r mit dem Namen
		// -2: Kategorie bereits vorhanden
		// -3: Konnte nicht gel�scht werden

		if (functionName.equals("new"))
		{
			String eingabe[] = paramS.split(controls.Globals.SEPARATOR);
			int i = database.Categories.newKategorie(eingabe[1], eingabe[0]);
			refreshViews();
			return i;
		}
		else if (functionName.equals("delete"))
		{
			if (database.Categories.delKategorie(paramS))
			{
				refreshViews();
				return 2;
			}
			else
			{
				return -3;
			}
		}

		return 0;
	}

	@Override
	public ArrayList<String> getDataList (String query)
	{
		// query = Name der T�r
		// Return: ArrayList<String> mit allen Kategorien

		return database.Categories.getKategorien(query);
	}
}
