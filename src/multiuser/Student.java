package multiuser;

import java.util.ArrayList;

import database.sql.Attribute;

public class Student extends User
{
	public Student(Attribute attribute, String name)
	{
		super(attribute,name);
	}

	private Double Highscore;
	private ArrayList<String> Karteien = new ArrayList<String>();
	
	// TODO : Wenn Datenbank funktioniert diesen auslesen
	public void getHighscore() {
		
	}
	
	// TODO : Wenn Datenbank fertig in DB speichern
	public void setHighscore(String newHighscore) {
		
	}
	
	// TODO : Wenn DB funktioniert, muss man hier die Karteien des Users aus DB lesen 
	public void getKarteienListe() {
		
	}
	
	// TODO : Wenn DB funktioniert, muss man hier die Karteien des Users updaten k�nnen 
	public void updateKarteien() {
		
	}
}
