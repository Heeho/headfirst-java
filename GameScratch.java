import java.util.Timer;
import java.util.TimerTask;

public class GameScratch {
	public static void main(String[] args) {
		GUI gui = new GUI();
	}
}

class GUI {
	int MAP_SIZE, VIEW_SIZE;
	
	int playerLoc, playerDest;
	int[] thingsMap;
	int[] thingsSet;
	
	public GUI() {
		Map m = new Map(MAP_SIZE, VIEW_SIZE);
		m.fillThingsMap(thingsMap);
		
		Timer t1 = new Timer();
		TimerTask shower = new TimerTask() {
			public void run() {
				//uses thingsMap, playerLoc
				for(int thg: thingsMap) {
					if(true) {
						thingsSet.add(thg);
					} else {
						thingsSet.remove(thg);
					}
				}
			}
		};
		t1.shedule(shower, 0, 15);
	
		Timer t2 = new Timer();
		TimerTask walker = new TimerTask() {
			public void run() {
				//uses playerLoc, playerDest
				playerLoc.translate(dX, dY);			
			}
		};
		t2.shedule(walker, 0, 200);
	}//constructor
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void actionPerformed(ActionEvent ev) {
			Point p = new Point();
			playerDest.setLocation(p);
		}	
	}
	
	class Thing /*extends JLabel*/ {
		Point mapLoc;
		public Thing(Point p) {
			mapLoc = p;
			
			//swing timer
			Swing.Timer t = new Timer(15, new ActionListener() {
				public void run() {
					//uses walker dX, dY;
					//sets object location in view
					this.setLocation(this.getLocation().translate(dX, dY));
				}	
			});
			t.start();
		}
	}
}

class Map {
	int mapSize;
	int viewSize;
	
	public Map(int m, int v) {
		mapSize = m;
		viewSize = v;
	}
	
	public void fillThingsMap(int[] tm) {
		//generate obj's and their loc's
		//into hashmap?
	}
}
