import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.awt.*;

public class GameScratch {
	public static void main(String[] args) {
		SwingUtilities.envokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
			}
		});
	}
}

class GUI {
	int MAP_SIZE, VIEW_SIZE;
	int picSize = 20;

	private Color color = Color.BLACK;

	JFrame f;
	Walker w;
	
	public GUI() {
		go();
	}
	
	public void go() {
		f = new JFrame("ScrollGame");
		
		w = new Walker();
		Map.fillMap(MAP_SIZE, VIEW_SIZE, w.thgMap);

		f.setVisible(true);
	}//go
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void actionPerformed(ActionEvent ev) {
			Point p = new Point();
			pDest.setLocation(p);
		}	
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(color);
		g2d.fillRect(VIEW_SIZE/2 - picSize/2, VIEW_SIZE/2 - picSize/2, picSize, picSize);
		this.getToolkit().sync();
	}

	//player character
	public void paintComponent(Graphics g) {}

	private void show() {}
}

class Thing extends JLabel {
	Point tLoc;
		
	public Thing(Point p) {
		tLoc = p;
	}
}

class Walker {
	int speed = 200;
	
	Point pLoc, pDest;
	
	ArrayList<Thing> thgMap;
	
	int deltaX = pLoc.getX() - pDest.getX();
	int deltaY = pLoc.getY() - pDest.getY();

	int dX, dY;
	
	public Walker() {
		Timer t1 = new Timer();
		TimerTask walker = new TimerTask() {
			public void run() {
				walk();
			}
		};
		t1.shedule(walker, 0, speed);
	}
	
	public void walk(JLayeredPane screen) {
		SwingUtilities.envokeLater(new Runnable(){
			public void run() {
				show();
			}
		});
	}
	
	private void show() {}
}

class Map {
	public static <T extends Thing> void filler(int mapSize, int viewSize, ArrayList<Thing> thgMap) {
		while(true) {		
			thgMap.add(<T>, randLoc());
		}
	}
	
	public static Point randLoc() {}
}
