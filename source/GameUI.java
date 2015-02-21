import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

/**
 * This class represents the GameUI in game panel, which consists of the Timer and some menus.
 * @author Vinh Doan
 *
 */

public class GameUI{
	private int x;
	private int y;
	private boolean onlyOne;
	private static int time, players = 0;
	

	private static String winnerDetermined;
	

	/**
	 * Creates GameUI
	 */
	public GameUI() {
		winnerDetermined = "";
	}

	public static void setPlayers(int players) {
		GameUI.players = players;
	}
	
	public void setOnlyOne(boolean onlyOne) {
		this.onlyOne = onlyOne;
	}
	
	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}

	public static void setWinnerDetermined(String winnerDetermined) {
		GameUI.winnerDetermined = winnerDetermined;
	}
	
	public int getTime() {
		return time;
	}


	public static void setTime(int t) {
		time = t;
	}
	
	/**
	 * Draws Game UI
	 * @param g - Graphics to draw with
	 * @param w - width (should be half of screen)
	 * @param h - height
	 */
	public void draw(Graphics g, int w, int h) {
		int timeShift =0;
		if(onlyOne)
			timeShift = (int) (h * .4);
		int minutes = time / 60;
		int seconds = time % 60;
		String timeString  = String.format("%02d:%02d", minutes, seconds);
		Font f = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(w - fm.stringWidth(timeString) / 2, h / 2 - 20 + timeShift, fm.stringWidth(timeString) + 2, 65);
		g2.setColor(Color.WHITE);
		g2.drawString(timeString, w - fm.stringWidth(timeString) / 2, h /2 + 25 + timeShift);
		String players = "Players: " + GameUI.players;
		g.setFont(new Font("SansSerif", Font.BOLD, 12) );
		fm = g.getFontMetrics();
		g2.drawString(players, w - fm.stringWidth(players) / 2, h /2 + 25 + timeShift + 15);
		
		if (!winnerDetermined.equals(""))
		{
			g.setColor(new Color(0 ,0 ,0, 125));
			g.fillRect(0, 0, w * 2,(int)( h * 1.5));
			g.setColor(Color.WHITE);
			String winnerText  = winnerDetermined;
			Font f2 = new Font("SansSerif", Font.BOLD, 80);
			g.setFont(f2);
			fm = g.getFontMetrics();
			g.drawString(winnerText, w - fm.stringWidth(winnerText) / 2, h /2);
			String menuText  = "MENU";
			String restartText  = "RESTART";
			Font f3 = new Font("SansSerif", Font.BOLD, 50);
			g.setFont(f3);
			fm = g.getFontMetrics();
			g.drawString(menuText, (int) (w * .25 * 2 - fm.stringWidth(menuText) / 2), (int) (h * .75));
			g.drawString(restartText, (int) (w * .75  * 2- fm.stringWidth(restartText) / 2 ),  (int) (h * .75));
			
		}
		else if (time == 0)
		{
			g.setColor(new Color(0 ,0 ,0, 125));
			g.fillRect(0, 0, w * 2,(int)( h * 1.5));
			g.setColor(Color.WHITE);
			String roundText  = "ROUND OVER";
			Font f2 = new Font("SansSerif", Font.BOLD, 80);
			g.setFont(f2);
			fm = g.getFontMetrics();
			g.drawString(roundText, w - fm.stringWidth(roundText) / 2, h /2);
			
			String nextText  = "THE NEXT ROUND WILL BEGIN SHORTLY";
			Font f3 = new Font("SansSerif", Font.BOLD, 20);
			g.setFont(f3);
			fm = g.getFontMetrics();
			g.drawString(nextText, w - fm.stringWidth(nextText) / 2, h /2 + 50);
		}
	}
}
