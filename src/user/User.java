package user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Constants;
import database.UserLogin;

public class User
{

	private static String Username; // Username
	private static String Email; // Email
	private static String Password; // Password

	// Zum Registrieren
	private static Boolean isOk;
	
	private static String[] genData = new String[3];

	public static Boolean Register(String Userdata, Boolean istLehrer)
	{

		String[] Data = genArray(Userdata);
		if (validateUsername() && validatePassword() && validateEmail())
		{
			if (UserLogin.checkPossible(Data))
			{
				UserLogin.newUser(Data, istLehrer);
				isOk = true; // Wenn Success
			} else
			{
				System.out.println("Username oder E-Mail bereits vorhanden / verwendet");
				isOk = false; // Wenn bereits vorhanden
			}
		}

		return isOk;
	}

	private static String[] genArray(String toGenerate)
	{
		genData = toGenerate.split(Constants.SEPARATOR);
		
		return genData;
	}

	// Zum Einloggen im Programm
	private static Boolean isCorrect;

	public static Boolean Login(String LoginData)
	{
		
		String[] Data = genArray(LoginData);
		isCorrect = UserLogin.loginUser(Data);
		if (isCorrect)
		{
			new Profil(); //Momentan noch eine Sackgasse --> Date 28.04.2016
			System.out.println("Es funzt ;)");
		} else
		{
			System.out.println("Sorry die Eingaben sind nicht korrekt");
		}

		return isCorrect;
	}

	// Zum L�schen von Benutzern --> Nur m�glich wenn man eingeloggt ist
	private static Boolean isDeleted;

	public static Boolean Delete(String Username)
	{

		try
		{
			UserLogin.delUser(Username);
			isDeleted = true; // if Successfull deleted
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			isDeleted = false;
		}

		return isDeleted; 
	}

	// Validiert Username mit Regex
	private static boolean validateUsername()
	{
		// Validierung Username
		final String R_USERNAME = "^[A-Za-z0-9]{4,30}$";
		Pattern pUsername = Pattern.compile(R_USERNAME);
		Matcher mUsername = pUsername.matcher(Username);
		if (mUsername.matches())
		{
			System.out.println("Username: True");
			return true;
		} else
		{
			System.out.println("Username: false");
			return false;
		}
	}

	// validiert Password mit Regex
	private static boolean validatePassword()
	{
		// Validierung Password
		final String R_PASSWORD = "^[A-Za-z0-9!?+*,�%&=]{8,50}$";
		Pattern pPassword = Pattern.compile(R_PASSWORD);
		Matcher mPassword = pPassword.matcher(Password);
		if (mPassword.matches())
		{
			System.out.println("Password: True");
			return true;
		} else
		{
			System.out.println("Password: false");
			return false;
		}
	}

	// validiert E-Mail mit Regex
	private static boolean validateEmail()
	{
		// Validierung Email
		final String R_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pEmail = Pattern.compile(R_EMAIL);
		Matcher mEmail = pEmail.matcher(Email);
		if (mEmail.matches())
		{
			System.out.println("Email: True");
			return true;
		} else
		{
			System.out.println("Email: false");
			return false;
		}
	}
}