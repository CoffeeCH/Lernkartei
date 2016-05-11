package Learning;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JFrame;

public class portionErstellen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton Portionw�hlen = new JButton("Portion w�hlen");
	JTextField Portioneingabe = new JTextField();
	JLabel erkl�rung = new JLabel("Geben sie die Gew�nschte Portion ein");
	int Portionengr�sse;

	public portionErstellen(int Portionengr�sse) {

		this.getContentPane().add(erkl�rung, BorderLayout.NORTH);
		this.getContentPane().add(Portionw�hlen, BorderLayout.SOUTH);
		this.getContentPane().add(Portioneingabe, BorderLayout.CENTER);
		Portionw�hlen.addActionListener(this);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Portionw�hlen) {
			Portionengr�sse = (Integer.parseInt(Portioneingabe.getText()));
			System.out.println(Integer.toString(Portionengr�sse));
		}

	}

}
