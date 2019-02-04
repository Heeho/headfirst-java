import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
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
		
		w = new Walker(/*args*/);
		Map.fillMap(MAP_SIZE, VIEW_SIZE);

		f.setVisible(true);
	}//go
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void actionPerformed(ActionEvent ev) {
			Point p = new Point();
			w.dest.setLocation(p);
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
}

class Thing extends JLabel {
	Point tLoc;
		
	public Thing(Point p) {
		tLoc = p;
	}
}

class Walker {}

class Map {
	ArrayList<Thing> thgMap= new ArrayList<Thing>();
	public static void fillMap(int mapSize, int viewSize) {
		while(true) {		
			thgMap.add(new Thing(randLoc()));
		}
	}
	
	public static Point randLoc() {
		return new Point();
	}
}
