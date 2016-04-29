package categories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class kategorienErstellen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JFrame CreateKatg = new JFrame();

	JButton katgeorieErstellen = new JButton("Kasten Erstellen");
	JButton Abbrechen = new JButton("Abbrechen");

	// JButton kategorieL�schen = new JButton("Kasten L�schen");

	JTextField kategorieEingabe = new JTextField();

	// Hier dann Kasten einf�gen
	String Eingabe = new String();

	public kategorienErstellen() {

		new kategorienAbrufen(CreateKatg);

		katgeorieErstellen.addActionListener(this);
		kategorieEingabe.addActionListener(this);
		Abbrechen.addActionListener(this);
		// kategorieL�schen.addActionListener(this);
		CreateKatg.getContentPane().setLayout(null);
		CreateKatg.getContentPane().add(katgeorieErstellen);
		katgeorieErstellen.setBounds(25, 80, 200, 25);
		CreateKatg.getContentPane().add(kategorieEingabe);
		kategorieEingabe.setBounds(25, 50, 440, 20);
		CreateKatg.getContentPane().add(Abbrechen);
		Abbrechen.setBounds(265, 80, 200, 25);

		// MainFrame.getContentPane().add(kategorieL�schen);

		CreateKatg.setDefaultCloseOperation(EXIT_ON_CLOSE);

		CreateKatg.setSize(500, 250);
		CreateKatg.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Eingabe = kategorieEingabe.getText();

		if (e.getSource() == katgeorieErstellen)
		// Neuen Kasten erstellen
		{
			// �berschrift f�r den Kasten einf�gen und in SQL einf�gen
			// Hier in KAtegorien Tabelle einf�gen SQL..

			database.Categories.newKategorie(Eingabe, "Franz");

			System.out.println(Eingabe);

			// Kasten Neben anderem Visible Einf�gen
		} else if (e.getSource() == Abbrechen) {
			CreateKatg.setVisible(false);
		}

		// else if (e.getSource() == kategorieL�schen) {
		// Eingabe = "";
		// kategorieEingabe.setText("");
		// }

	}

}
