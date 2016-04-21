package quizlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.*;


/**
 * Klasse um HTTP Requests auszuf�hren.
 * 
 * @author ma
 *
 */
public class Request
{
	public static String		charset	= "UTF-8";
	private static JSONObject	obj;

	/**
	 * F�hrt eine Get-Abfrage durch
	 * 
	 * @param url
	 *            Kompletter URL f�r die Abfrage. Muss korrekt sein, sonst wird
	 *            ein Fehler ausgel�st
	 * @return Antwort als JSON Object, null wenn es einen Fehler gibt
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static JSONObject GetJSONObject (String url) throws MalformedURLException, IOException
	{
		// �ffnet die Verbindung
		URLConnection connection = new URL(url).openConnection();
		// Setzt die Charset Property
		connection.setRequestProperty("Accept-Charset", charset);
		// Speichert das Resultat
		InputStream response = connection.getInputStream();

		// Erzeugt das JSONObject und gibt es zur�ck
		try (Scanner scanner = new Scanner(response))
		{
			String responseBody = scanner.useDelimiter("\\A").next();
			obj = new JSONObject(responseBody);
			return obj;
		}
		catch (Exception e)
		{
			System.out.println("Error (Request):");
			System.out.println(e.getMessage());
			return null;
		}
	}
}
