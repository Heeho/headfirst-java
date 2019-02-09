import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class GameScratch {
	public static int 	MAP_XY = 2400,
					VIEW_SIZE = 600;

	public static void main(String[] args) {
		Map.fillMap(MAP_XY);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
			}
		});
	}
}

class GUI {
	JFrame f;
	JLayeredPane screen;
	Player p1;
	
	public GUI() {
		go();
	}
	
	public void go() {
		f = new JFrame("ScrollGame");
		f.setSize(GameScratch.VIEW_SIZE, GameScratch.VIEW_SIZE);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);

		screen = new JLayeredPane();
		screen.setLayout(null);
		screen.setOpaque(true);
		screen.setBackground(Color.decode("#defcec"));
		screen.setPreferredSize(f.getSize());

		p1 = new Player(screen);
		int pLocOnScreen = GameScratch.VIEW_SIZE/2 - p1.picSize;

		p1.setSize(screen.getPreferredSize());
		p1.setOpaque(false);
		p1.setLocation(pLocOnScreen, pLocOnScreen);
		screen.add(p1, JLayeredPane.DEFAULT_LAYER);

		f.getContentPane().add(screen, BorderLayout.CENTER);
		f.setVisible(true);
	}//go
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void actionPerformed(ActionEvent ev) {
			Point p = new Point();
			p1.pDest.setLocation(p);
		}	
	}
}




