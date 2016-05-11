package Learning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Zufallszieher extends JFrame implements ActionListener {

	public static int rdm = 0;
	private static final long serialVersionUID = 1L;

	JButton Portionw�hlen = new JButton("Portion w�hlen");
	JButton Abbrechen = new JButton("Abbrechen");
	JButton unbegrenztlernen = new JButton("Ohne Portionen Lernen");
	JTextField Portioneingabe = new JTextField();
	JLabel erkl�rung = new JLabel("Geben sie die Gew�nschte Portion ein");
	int Portionengr�sse = 0;

	//Zufallszhl erstellen
	public static int ziehen() {
		int zufallszahl = 0;

		//zufallszahl = (int) ((Math.random() * Database.pullFromStock().size()));

		return zufallszahl;
	}

	public Zufallszieher() {

		this.getContentPane().add(erkl�rung);
		erkl�rung.setBounds(20, 20, 300, 30);

		this.getContentPane().add(Portionw�hlen);
		Portionw�hlen.setBounds(25, 100, 200, 25);

		this.getContentPane().add(Portioneingabe);
		Portioneingabe.setBounds(25, 60, 200, 25);

		this.getContentPane().add(Abbrechen);
		Abbrechen.setBounds(250, 100, 200, 25);

		this.getContentPane().add(unbegrenztlernen);
		unbegrenztlernen.setBounds(250, 60, 200, 25);

		this.getContentPane().setLayout(null);
		Portionw�hlen.addActionListener(this);
		Abbrechen.addActionListener(this);
		unbegrenztlernen.addActionListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 250);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Portionw�hlen) {
			Portionengr�sse = (Integer.parseInt(Portioneingabe.getText()));
			ziehen();
			new Bewertungsklasse();

		}

		else if (e.getSource() == Abbrechen) {
			this.setVisible(false);
		} else if (e.getSource() == unbegrenztlernen) {

			new Bewertungsklasse();
		}

	}

}
