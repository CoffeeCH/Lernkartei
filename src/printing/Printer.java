/*
 * @Author Yanis  
 */
package printing;

import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import database.LKDatabase;

public class Printer extends JFrame {

	public static final long serialVersionUID = 1L;

	public void printer(String Stack) {
		
				druckeKartenset(Stack);
			
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				dispose();
			}
		});
	}

	public void druckeKartenset(String Stack) {
		int kartenID = -1;
		double anzKarten;
		long  anzDurchf�hrungen;
		ArrayList<String[]> cards = LKDatabase.myCards.pullFromStock(Stack);
		anzKarten = cards.size();
		anzDurchf�hrungen = ((Math.round(anzKarten / 8 + 1)) >= Math.round(anzKarten / 8))? Math.round(anzKarten / 8) : Math.round(anzKarten / 8 + 1);

		for(int i = 0; i < anzDurchf�hrungen; i++)
		{
			printPage(Stack, kartenID);
		}
	}
	private void printPage(String Stack, int kartenID)
	{
		//TODO soll mehrere karten drucken 
		  	
		PrintJob prjob = getToolkit().getPrintJob(this, "KartenDruck", null);
		
		if (null != prjob) {
			final int iPageWidth = 842;
			final int iPageHeight = 595;
	

			Graphics pg = prjob.getGraphics();

			if (null != pg && 0 < 1) {
				int iPageHeightQuarter = iPageHeight / 4;
				int iPageWidthQuarter = iPageWidth / 4;

				int iPageHeightEight = iPageHeight / 8;
				int iPageWidthEight = iPageWidth / 16;

				// Senkrechte Linien
				for (int i = 0; i < 3; i++) {
					pg.drawLine(iPageWidthQuarter, 0, iPageWidthQuarter, iPageHeight);
					iPageWidthQuarter += iPageWidth / 4;
				}
				// Waagrechte Linien
				for (int a = 0; a < 3; a++) {
					pg.drawLine(0, iPageHeightQuarter, iPageWidth, iPageHeightQuarter);
					iPageHeightQuarter += iPageHeight / 4;
				}
				// Text Einfi�gen
				for (int b = 0; b < 4; b++) {

					iPageWidthEight = iPageWidth / 10;
					for (int c = 0; c < 2; c++) {
						kartenID += 1;
						String[] VorderUndR�ckseite = LKDatabase.myCards.getFrontAndBackside(Stack, kartenID);

						pg.drawString(VorderUndR�ckseite[0], iPageWidthEight, iPageHeightEight);

						iPageWidthEight += iPageWidth / 4;

						pg.drawString(VorderUndR�ckseite[1], iPageWidthEight, iPageHeightEight);

						iPageWidthEight += iPageWidth / 4;
					}

					iPageHeightEight += iPageHeight / 4;
				}
				pg.dispose();
			}
			prjob.end();
		}		
	}
}