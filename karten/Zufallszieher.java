package karten;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Zufallszieher extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton Portionw�hlen = new JButton("Portion w�hlen");
	JTextField Portioneingabe = new JTextField();
	JLabel erkl�rung = new JLabel("Geben sie die Gew�nschte Portion ein");
	int Portionengr�sse = 0;

	public Zufallszieher() {

		this.getContentPane().add(erkl�rung, BorderLayout.NORTH);
		this.getContentPane().add(Portionw�hlen, BorderLayout.SOUTH);
		this.getContentPane().add(Portioneingabe, BorderLayout.CENTER);
		Portionw�hlen.addActionListener(this);
		this.setTitle("Portion");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Portionw�hlen) {
			Portionengr�sse = (Integer.parseInt(Portioneingabe.getText()));
			System.out.println(Integer.toString(Portionengr�sse));


			for (int i = 1; i < Portionengr�sse; i++) {
				int zufall = 1 + (int) (Math.random() * Portionengr�sse);
				System.out.println(zufall);
			}
		}
	}

}
